package jpa.practice.relationship.query_plan_cache.service;


import jpa.practice.relationship.query_plan_cache.entity.Author;
import jpa.practice.relationship.query_plan_cache.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;

@Service
public class BookstoreService {
    private final AuthorRepository authorRepository;
    private final HibernateStatisticsService hibernateStatisticsService;
    public BookstoreService(AuthorRepository authorRepository, HibernateStatisticsService hibernateStatisticsService) {
        this.authorRepository = authorRepository;
        this.hibernateStatisticsService = hibernateStatisticsService;
    }

    /**
     * Elapsed time: 1228 ms
     * Entity Fetch Count: 0
     * Query Execution Count: 10000
     * Query Plan Cache Hit Count: 19998 //쿼리 실행시 캐시에서 가져옴
     * Query Plan Cache Miss Count: 2 //2번 실제 DB에서 쿼리 실행
     * Second Level Cache Hit Count: 0
     * Second Level Cache Miss Count: 0
     */
    // adjust the Query Plan Cache size from application.properties
    public void fetchAuthorsByGenre() {
        hibernateStatisticsService.clear();

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 1; i <= 5000; i++) {
            List<Author> authorsByGenre = authorRepository.fetchByGenre("Anthology");
            List<Author> authorsByAge = authorRepository.fetchByAge(40);
        }
        sw.stop();

        System.out.println("Elapsed time: " + sw.getTotalTimeMillis() + " ms");
        // Hibernate 통계 출력
        hibernateStatisticsService.printStatistics();
    }

    public void fetchAuthorsByAge() {
        hibernateStatisticsService.clear();

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 1; i <= 5000; i++) {
            List<Author> authorsByAge = authorRepository.fetchByAge(40);
            List<Author> authorsByGenre = authorRepository.fetchByGenre("Anthology");
        }
        sw.stop();

        System.out.println("Elapsed time: " + sw.getTotalTimeMillis() + " ms");
        // Hibernate 통계 출력
        hibernateStatisticsService.printStatistics();
    }

    /**
     *
     * spring.jpa.properties.hibernate.query.in_clause_parameter_padding=true
     *
     * # In Padding 미적용시
     *
     * [Hibernate]
     *     select
     *         a1_0.id,
     *         a1_0.age,
     *         a1_0.genre,
     *         a1_0.name
     *     from
     *         author a1_0
     *     where
     *         a1_0.id in (1,2,3)
     *
     * [Hibernate]
     *     select
     *         a1_0.id,
     *         a1_0.age,
     *         a1_0.genre,
     *         a1_0.name
     *     from
     *         author a1_0
     *     where
     *         a1_0.id in (1,2,3,4)
     *
     * Entity Fetch Count: 0
     * Query Execution Count: 9
     * Query Plan Cache Hit Count: 9
     * Query Plan Cache Miss Count: 0
     * Second Level Cache Hit Count: 0
     * Second Level Cache Miss Count: 0
     *
     * # In Padding 적용시
     * Entity Fetch Count: 0
     * Query Execution Count: 9
     * Query Plan Cache Hit Count: 9
     * Query Plan Cache Miss Count: 0
     * Second Level Cache Hit Count: 0
     * Second Level Cache Miss Count: 0
     *
     * //In 1,2,3
     * [Hibernate]
     *     select
     *         a1_0.id,
     *         a1_0.age,
     *         a1_0.genre,
     *         a1_0.name
     *     from
     *         author a1_0
     *     where
     *         a1_0.id in (1,2,3,3)
     * //In 1,2,3,4
     * [Hibernate]
     *     select
     *         a1_0.id,
     *         a1_0.age,
     *         a1_0.genre,
     *         a1_0.name
     *     from
     *         author a1_0
     *     where
     *         a1_0.id in (1,2,3,4)
     *
     * 생각과는 다르게 Parameter로 전달되는 IN List의 크기에 따라 Query Plan Cache Hit Count가 감소하지 않는다.
     */

    @Transactional(readOnly=true)
    public void fetchAuthorsIn() {
        hibernateStatisticsService.clear();
        List twoIds = List.of(1L, 2L);
        List threeIds = List.of(1L, 2L, 3L);
        List fourIds = List.of(1L, 2L, 3L, 4L);
        List fiveIds = List.of(1L, 2L, 3L, 4L, 5L);
        List sixIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);
        List sevenIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        List eightIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List nineIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        List tenIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        authorRepository.fetchIn(twoIds);
        authorRepository.fetchIn(threeIds);
        authorRepository.fetchIn(fourIds);
        authorRepository.fetchIn(fiveIds);
        authorRepository.fetchIn(sixIds);
        authorRepository.fetchIn(sevenIds);
        authorRepository.fetchIn(eightIds);
        authorRepository.fetchIn(nineIds);
        authorRepository.fetchIn(tenIds);

        hibernateStatisticsService.printStatistics();
    }
}