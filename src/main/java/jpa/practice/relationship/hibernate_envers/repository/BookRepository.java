package jpa.practice.relationship.hibernate_envers.repository;


import jpa.practice.relationship.hibernate_envers.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, RevisionRepository<Book, Long, Integer> {
}