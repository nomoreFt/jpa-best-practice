package jpa.practice.relationship.slow_query_analyzer.repository;


import jpa.practice.relationship.slow_query_analyzer.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long>{

    @Query(value = "SELECT * FROM  author WHERE sleep(5000)", nativeQuery = true)
    Author largeQuery();
}
