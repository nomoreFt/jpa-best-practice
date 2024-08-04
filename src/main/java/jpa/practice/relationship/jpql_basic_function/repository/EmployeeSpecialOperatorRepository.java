package jpa.practice.relationship.jpql_basic_function.repository;

import jpa.practice.relationship.jpql_basic_function.entity.Employee;
import jpa.practice.relationship.jpql_basic_function.entity.collection.Book;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeSpecialOperatorRepository {
    @Query("SELECT e.favoriteBooks FROM Employee e JOIN e.favoriteBooks fb WHERE INDEX(fb) = ?1")
    List<Book> index_function(int index);

    @Query("SELECT p FROM Employee e JOIN e.courses p WHERE KEY(p) = ?1")
    List<String> key_function(String key);

    @Query("SELECT e FROM Employee e WHERE SIZE(e.favoriteBooks) >= ?1")
    List<Employee> collectionSize_function(int size);

    @Query("SELECT e FROM Employee e WHERE e.courses IS EMPTY")
    List<Employee> isEmpty_function();

    @Query("SELECT e FROM Employee e WHERE e.courses IS NOT EMPTY")
    List<Employee> isNotEmpty_function();

    @Query("SELECT e FROM Employee e WHERE ?1 MEMBER OF e.favoriteBooks")
    List<Employee> memberOf_function(Book book);

}
