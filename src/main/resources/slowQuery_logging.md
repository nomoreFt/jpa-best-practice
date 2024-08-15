# DataSource-Proxy를 이용한 방법

## 의존성 추가
~~~groovy
implementation 'net.ttddyy:datasource-proxy:1.8.1'
~~~

## DataSource Proxy 설정

~~~java
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

/**
 * proxy로 query의 counting을 할 수 있도록 설정
 *
 */
@Configuration
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = Logger.getLogger(DatasourceProxyBeanPostProcessor.class.getName());
    private static final long THRESHOLD_MILLIS = 30;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource) {

            logger.info("DataSource bean has been found: " + bean);

            final ProxyFactory proxyFactory = new ProxyFactory(bean);

            proxyFactory.setProxyTargetClass(true);
            proxyFactory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));

            return proxyFactory.getProxy();
        }
        return bean;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    private static class ProxyDataSourceInterceptor implements MethodInterceptor {
        private final DataSource dataSource;

        public ProxyDataSourceInterceptor(final DataSource dataSource) {
            super();

            this.dataSource = ProxyDataSourceBuilder.create(dataSource)
                    .name("DATA_SOURCE_PROXY")
                    .logQueryBySlf4j(SLF4JLogLevel.INFO)
                    .multiline()
                    .countQuery()
                    .listener(getSlowQueryListener())
                    .build();
        }


        /**
         * 해당 Listener를 통해 slow query를 감지하고 로깅한다.
         * 
         * INFO 46864 --- [           main] n.t.d.l.l.SLF4JQueryLoggingListener      :
         * Name:DATA_SOURCE_PROXY, Connection:6, Time:5006, Success:True
         * Type:Prepared, Batch:False, QuerySize:1, BatchSize:0
         * Query:["SELECT * FROM  author WHERE sleep(5000)"]
         * Params:[()]
         *
         * WARN Slow query detected: SELECT * FROM  author WHERE sleep(5000)
         * WARN Name:DATA_SOURCE_PROXY, Connection:6, Time:5006, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["SELECT * FROM  author WHERE sleep(5000)"], Params:[()]
         *
         */
        private static SLF4JQueryLoggingListener getSlowQueryListener() {
            SLF4JQueryLoggingListener slowQueryListener = new SLF4JQueryLoggingListener(){
                @Override
                public void afterQuery(final ExecutionInfo execInfo, final List<QueryInfo> queryInfoList) {
                    queryInfoList.forEach(queryInfo -> {
                        if (THRESHOLD_MILLIS <= execInfo.getElapsedTime()) {
                            logger.warn("Slow query detected: " + queryInfo.getQuery());
                            super.afterQuery(execInfo, queryInfoList);
                        }
                    });
                }
            };
            slowQueryListener.setLogLevel(SLF4JLogLevel.WARN);
            return slowQueryListener;
        }

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            final Method proxyMethod = ReflectionUtils.
                    findMethod(this.dataSource.getClass(), invocation.getMethod().getName());

            if (proxyMethod != null) {
                return proxyMethod.invoke(this.dataSource, invocation.getArguments());
            }

            return invocation.proceed();
        }
    }
}
~~~


## 예시 로그

h2에서 추가된 sleep 기능을 통해 의도적으로 지연을 발생시킨다. 

~~~sql
INFO 46864 --- [           main] n.t.d.l.l.SLF4JQueryLoggingListener      :
Name:DATA_SOURCE_PROXY, Connection:6, Time:5006, Success:True
Type:Prepared, Batch:False, QuerySize:1, BatchSize:0
Query:["SELECT * FROM  author WHERE sleep(5000)"]
Params:[()]

WARN Slow query detected: SELECT * FROM  author WHERE sleep(5000)
WARN Name:DATA_SOURCE_PROXY, Connection:6, Time:5006, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["SELECT * FROM  author WHERE sleep(5000)"], Params:[()]
        
    
~~~






# hibernate 프로퍼티 설정

## yml에 설정을 추가한다.

~~~yml
spring.jpa.properties.hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS: 5000
~~~


## 예시 로그

~~~sql
2024-08-15T12:51:38.658+09:00  INFO 48111 --- [           main] org.hibernate.SQL_SLOW                   : Slow query took 5010 milliseconds [SELECT * FROM  author WHERE sleep(5000)]
Slow query took 5010 milliseconds [SELECT * FROM  author WHERE sleep(5000)]
~~~