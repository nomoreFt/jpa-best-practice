package jpa.practice.relationship.sqlcount_assert.config;


import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CleanUp {

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    public CleanUp(JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @Transactional
    public void all() {
        List<String> tables = entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getName())
                .collect(Collectors.toList());

        for (String table : tables) {
            jdbcTemplate.execute("TRUNCATE table " + table);
        }
    }
}