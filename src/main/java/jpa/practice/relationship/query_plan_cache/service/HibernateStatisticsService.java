package jpa.practice.relationship.query_plan_cache.service;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateStatisticsService {

    private final Statistics statistics;

    @Autowired
    public HibernateStatisticsService(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        this.statistics = sessionFactory.getStatistics();
        this.statistics.setStatisticsEnabled(true); // 통계 활성화
    }

    //clear
    public void clear() {
        statistics.clear();
    }

    public void printStatistics() {
        System.out.println("Entity Fetch Count: " + statistics.getEntityFetchCount());
        System.out.println("Query Execution Count: " + statistics.getQueryExecutionCount());
        System.out.println("Query Plan Cache Hit Count: " + statistics.getQueryPlanCacheHitCount());
        System.out.println("Query Plan Cache Miss Count: " + statistics.getQueryPlanCacheMissCount());
        System.out.println("Second Level Cache Hit Count: " + statistics.getSecondLevelCacheHitCount());
        System.out.println("Second Level Cache Miss Count: " + statistics.getSecondLevelCacheMissCount());
    }
}