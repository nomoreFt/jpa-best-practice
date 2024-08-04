package jpa.practice.relationship.jpql_basic_function.repository;

import jpa.practice.relationship.jpql_basic_function.entity.Employee;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface EmployeeDateRepository {
    @Query("SELECT e FROM Employee e WHERE e.startDate = ?1")
    List<Employee> findEmployeesByStartDate(LocalDate startDate);

    @Query("SELECT e FROM Employee e WHERE e.startTime = ?1")
    List<Employee> findEmployeesByStartTime(LocalTime startTime);

    @Query("SELECT e FROM Employee e WHERE e.startDateTime = ?1")
    List<Employee> findEmployeesByStartDateTime(LocalDateTime startDateTime);

    @Query("SELECT e FROM Employee e WHERE e.startInstant = ?1")
    List<Employee> findEmployeesByStartInstant(java.time.Instant startInstant);
}
