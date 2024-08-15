package jpa.practice.relationship.multi_datasource.repository.secondary;

import jpa.practice.relationship.multi_datasource.entity.secondary.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
