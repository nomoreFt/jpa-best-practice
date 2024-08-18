package jpa.practice.relationship.logging_persistence_context.repository;

import jpa.practice.relationship.logging_persistence_context.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
