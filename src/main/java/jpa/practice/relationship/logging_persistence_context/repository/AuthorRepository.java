package jpa.practice.relationship.logging_persistence_context.repository;


import jpa.practice.relationship.logging_persistence_context.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
