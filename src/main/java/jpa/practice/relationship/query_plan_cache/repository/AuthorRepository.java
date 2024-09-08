package jpa.practice.relationship.query_plan_cache.repository;


import jpa.practice.relationship.query_plan_cache.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.genre = ?1")
    List<Author> fetchByGenre(String genre);

    @Query("SELECT a FROM Author a WHERE a.age > ?1")
    List<Author> fetchByAge(int age);

    @Transactional(readOnly=true)
    @Query("SELECT a FROM Author a WHERE a.id IN ?1")
    List<Author> fetchIn(List<Long> ids);
}