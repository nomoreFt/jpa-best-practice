package jpa.practice.relationship.jpql_basic_function.repository;

import jpa.practice.relationship.jpql_basic_function.entity.Employee;
import jpa.practice.relationship.jpql_basic_function.service.EmployeeSpecialService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeDateRepository, EmployeeSpecialOperatorRepository {
    @Query("SELECT e FROM Employee e WHERE ABS(e.salary - 100) > ?1")
    List<Employee> abs_function(Double difference);

    @Query("SELECT e FROM Employee e WHERE CASE e.status WHEN 0 THEN 'active' WHEN 1 THEN 'consultant' ELSE 'unknown' END = ?1")
    List<Employee> case_function(String statusDescription);

    @Query("SELECT COALESCE(e.salary, 0) FROM Employee e")
    List<Double> coalesce_function();

    @Query("SELECT CONCAT(e.firstName, ' ', e.lastName) FROM Employee e")
    List<String> concat_function();

    @Query("SELECT LENGTH(e.lastName) FROM Employee e")
    List<Integer> length_function();

    @Query("SELECT LOCATE(?1, e.firstName) FROM Employee e")
    List<Integer> locate_function(String search);

    @Query("SELECT LOWER(e.lastName) FROM Employee e")
    List<String> lower_function();

    @Query("SELECT SQRT(e.salary) FROM Employee e")
    List<Double> sqrt_function();

    @Query("SELECT SUBSTRING(e.firstName, 2, 2) FROM Employee e")
    List<String> substring_function();

    @Query("SELECT TRIM(BOTH 'x' FROM 'xxhelloxx') AS trimmedString")
    List<String> trim1_function();

    @Query("SELECT TRIM(LEADING '-' FROM '--example') AS trimmedString")
    List<String> trim2_function();

    @Query("SELECT TRIM(TRAILING 'x' FROM 'helloxxx') AS trimmedString")
    List<String> trim3_function();


    @Query("SELECT UPPER(e.lastName) FROM Employee e")
    List<String> upper_function();


}
