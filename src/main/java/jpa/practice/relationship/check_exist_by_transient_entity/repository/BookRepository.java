package jpa.practice.relationship.check_exist_by_transient_entity.repository;


import jpa.practice.relationship.check_exist_by_transient_entity.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, QueryByExampleExecutor<Book> {
}