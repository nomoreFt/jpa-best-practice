package jpa.practice.relationship.slow_query_analyzer.slowquery_config;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FunctionProvider {
    private final EntityManager entityManager;

    public FunctionProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addFunction() {
        entityManager.createNativeQuery("CREATE ALIAS SLEEP FOR \"jpa.practice.relationship.slow_query_analyzer.slowquery_config.CustomFunctions.sleep\"")
                .executeUpdate();
    }
}
