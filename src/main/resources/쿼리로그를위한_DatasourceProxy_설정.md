
# DatasourceProxy 설정

## gradle에 추가
~~~groovy
implementation 'net.ttddyy:datasource-proxy:1.8.1'
~~~


## Proxy 코드 추가

~~~java
package jpa.practice.relationship.sqlcount_assert.config;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * proxy로 query의 counting을 할 수 있도록 설정
 *
 */
@Configuration
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = Logger.getLogger(DatasourceProxyBeanPostProcessor.class.getName());
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


    /**
     * ProxyDataSourceInterceptor
     * - DataSource를 Proxy로 감싸서 query를 counting할 수 있도록 설정
     * - query를 실행하기 전과 후에 log 등 작업이 가능하다.
     */
    private static class ProxyDataSourceInterceptor implements MethodInterceptor {
        private final DataSource dataSource;

        public ProxyDataSourceInterceptor(final DataSource dataSource) {
            super();
            this.dataSource = ProxyDataSourceBuilder.create(dataSource)
                    .name("DATA_SOURCE_PROXY")
                    .logQueryBySlf4j(SLF4JLogLevel.INFO)
                    .multiline()
                    .countQuery()
                    .beforeMethod(execInfo -> {
                        logger.info("Before method: " + execInfo.getMethod().getName());
                    })
                    .afterMethod(execInfo -> {
                        logger.info("After method: " + execInfo.getMethod().getName());
                    })
                    .beforeQuery((execInfo, queryInfoList) -> {
                        queryInfoList.forEach(queryInfo -> {
                            logger.info("beforeQuery Query executed: " + queryInfo.getQuery());
                        });
                    })
                    .afterQuery((execInfo, queryInfoList) -> {
                        queryInfoList.forEach(queryInfo -> {
                            logger.info("afterQuery Query executed: " + queryInfo.getQuery());
                        });
                    })
                    .build();
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


## 사용 로그 확인

~~~sql
Before method: getConnection
After method: getConnection
Before method: prepareStatement
After method: prepareStatement
Before method: hashCode
After method: hashCode
Before method: setLong
After method: setLong
Before method: executeQuery
beforeQuery Query executed: select a1_0.id,a1_0.age,a1_0.genre,a1_0.name from author a1_0 where a1_0.id=?

2024-08-09T20:23:57.752+09:00  INFO 16475 --- [           main] n.t.d.l.l.SLF4JQueryLoggingListener      : 
Name:DATA_SOURCE_PROXY, Connection:7, Time:0, Success:True
Type:Prepared, Batch:False, QuerySize:1, BatchSize:0
Query:["select a1_0.id,a1_0.age,a1_0.genre,a1_0.name from author a1_0 where a1_0.id=?"]
Params:[(1)]

afterQuery Query executed: select a1_0.id,a1_0.age,a1_0.genre,a1_0.name from author a1_0 where a1_0.id=?
After method: executeQuery
Before method: hashCode
After method: hashCode
Before method: hashCode
After method: hashCode
Before method: hashCode
After method: hashCode
Before method: isClosed
After method: isClosed
Before method: hashCode
After method: hashCode
Before method: getMaxRows
After method: getMaxRows
Before method: getQueryTimeout
After method: getQueryTimeout
Before method: close
After method: close
~~~

* 로그를 보면 Connection 부터 preparedstatement, hashcode 등 다양한 메서드 호출을 확인할 수 있다.<br>
* Name:DATA_SOURCE_PROXY, Connection:7, Time:0, Success:True
  * Name: 프록시 이름
  * Connection: 연결 번호
  * Time: 실행 시간
  * Success: 성공 여부
* Type:Prepared, Batch:False, QuerySize:1, BatchSize:0
  * Type: 쿼리 타입
  * Batch: 배치 여부
  * QuerySize: 쿼리 사이즈
  * BatchSize: 배치 사이즈
    