package jpa.practice.relationship.hibernate_envers.repository;


import jpa.practice.relationship.hibernate_envers.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * RevisionRepository를 상속받아서 Entity의 변경 이력을 조회할 수 있다.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, RevisionRepository<Author, Long, Integer> {
    Author findByName(String name);
}