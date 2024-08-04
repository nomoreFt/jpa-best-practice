package jpa.practice.relationship.jpql_basic_function.service;

import jpa.practice.relationship.jpql_basic_function.entity.Employee;
import jpa.practice.relationship.jpql_basic_function.repository.EmployeeDateRepository;
import jpa.practice.relationship.jpql_basic_function.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;


/**
 * Hibernate 5 이상에서는 이 타입들을 기본적으로 지원하므로 추가적인 설정 없이 사용할 수 있습니다.
 */
@Service
public class EmployeeDateService {
    private final EmployeeRepository employeeRepository;
    LocalDate startDate;
    LocalTime startTime;
    LocalDateTime startDateTime;
    Instant instant;


    public EmployeeDateService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        startDate = LocalDate.of(2021, 1, 1);
        startTime = LocalTime.of(9, 0);
        startDateTime = LocalDate.of(2021, 1, 1).atTime(9, 0);
        instant = LocalDate.of(2021, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByDate() {
        List<Employee> employeesByStartDate = employeeRepository.findEmployeesByStartDate(startDate);
        employeesByStartDate.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByTime() {
        List<Employee> employeesByStartTime = employeeRepository.findEmployeesByStartTime(startTime);
        employeesByStartTime.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByDateTime() {
        List<Employee> employeesByStartDateTime = employeeRepository.findEmployeesByStartDateTime(startDateTime);
        employeesByStartDateTime.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByInstant() {
        List<Employee> employeesByStartInstant = employeeRepository.findEmployeesByStartInstant(instant);
        employeesByStartInstant.forEach(System.out::println);
    }
}
