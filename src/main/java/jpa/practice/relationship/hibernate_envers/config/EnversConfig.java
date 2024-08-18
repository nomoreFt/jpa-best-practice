package jpa.practice.relationship.hibernate_envers.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AuditReader를 빈으로 등록한다.
 * AuditReader는 RevisionRepository를 상속받은 Repository 이외에
 * 추가적인 기능을 사용할 때 사용한다.
 */
@Configuration
public class EnversConfig {
    private final EntityManagerFactory entityManagerFactory;

    public EnversConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public AuditReader auditReader() {
        return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
    }

}
