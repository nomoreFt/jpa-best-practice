package jpa.practice.relationship.jpql_basic_function.service;

import jpa.practice.relationship.jpql_basic_function.entity.Employee;
import jpa.practice.relationship.jpql_basic_function.entity.Gender;
import jpa.practice.relationship.jpql_basic_function.entity.collection.Novel;
import jpa.practice.relationship.jpql_basic_function.entity.collection.TextBook;
import jpa.practice.relationship.jpql_basic_function.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void init() {
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalDateTime startDateTime = LocalDate.of(2021, 1, 1).atTime(9, 0);
        Instant instant = LocalDate.of(2021, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);

        Employee hyunwooKim = Employee.builder()
                .firstName("현우")
                .lastName("김")
                .startDate(startDate)
                .startTime(startTime)
                .startDateTime(startDateTime)
                .startInstant(instant)
                .salary(1000.0)
                .gender(Gender.MALE)
                .status(1)
                .build();

        Employee jiwonPark = Employee.builder()
                .firstName("지원")
                .lastName("박")
                .startDate(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(9, 0))
                .startDateTime(LocalDate.of(2021, 1, 1).atTime(9, 0))
                .startInstant(LocalDate.of(2021, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC))
                .salary(1000.0)
                .gender(Gender.FEMALE)
                .status(0)
                .build();

        hyunwooKim.addFavoriteBook(Novel.builder()
                .title("Spring Boot")
                .author("hyunwooKim")
                .genre("IT")
                .build());
        hyunwooKim.addFavoriteBook(Novel.builder()
                .title("Spring Security")
                .author("hyunwooKim")
                .genre("IT")
                .build());
        hyunwooKim.addFavoriteBook(TextBook.builder()
                .title("Spring Data JPA")
                .author("jiwonPark")
                .subject("IT")
                .build());

        jiwonPark.addFavoriteBook(TextBook.builder()
                        .title("Spring Data JPA")
                        .author("jiwonPark")
                        .subject("IT")
                        .build());
        jiwonPark.addFavoriteBook(Novel.builder()
                        .title("Spring Boot")
                        .author("hyunwooKim")
                        .genre("IT")
                        .build());

        hyunwooKim.addCourse("Backend", "Spring Framework");
        hyunwooKim.addCourse("Backend", "Spring Boot Framework");
        hyunwooKim.addCourse("Frontend", "React Framework");

        jiwonPark.addCourse("Backend", "Spring Framework");
        jiwonPark.addCourse("Frontend", "React Framework");


        employeeRepository.saveAllAndFlush(List.of(hyunwooKim, jiwonPark));
    }

    /**
     * ABS function
     *
     * @param difference
     */
    @Transactional(readOnly = true)
    public void fetchEmployeeBySalaryDifferenceGreaterThan(Double difference) {
        List<Employee> employees = employeeRepository.abs_function(difference);
        employees.forEach(System.out::println);
    }

    /**
     * CASE function
     *
     * @param statusDescription
     */
    @Transactional(readOnly = true)
    public void fetchEmployeeByStatusDescription(String statusDescription) {
        List<Employee> employees = employeeRepository.case_function(statusDescription);
        employees.forEach(System.out::println);
    }


    /**
     * COALESCE 함수를 사용하여 NULL 값을 0으로 대체
     */
    @Transactional(readOnly = true)
    public void fetchEmployeeBySalaryNotNull() {
        List<Double> coalesced = employeeRepository.coalesce_function();
        for (Double salary : coalesced) {
            System.out.println("COALESCE salary : " + salary);
        }
        /**
         * [Hibernate]
         *     select
         *         coalesce(e1_0.salary, 0)
         *     from
         *         employee e1_0
         * COALESCE salary : 1000.0
         * COALESCE salary : 1000.0
         */
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByConcat() {
        List<String> concatenated = employeeRepository.concat_function();
        for (String name : concatenated) {
            System.out.println("CONCAT name : " + name);
        }
        /**
         * [Hibernate]
         *     select
         *         concat(e1_0.first_name, ' ', e1_0.last_name)
         *     from
         *         employee e1_0
         * CONCAT name : 현우 김
         * CONCAT name : 지원 박
         */
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByLength() {
        List<Integer> lengths = employeeRepository.length_function();
        for (Integer length : lengths) {
            System.out.println("LENGTH lastName : " + length);
        }
        /**
         * [Hibernate]
         *     select
         *         character_length(e1_0.last_name)
         *     from
         *         employee e1_0
         * LENGTH lastName : 1
         * LENGTH lastName : 1
         */
    }


    /**
     * LOCATE 함수는 문자열 검색 및 위치 찾기 기능
     */
    @Transactional(readOnly = true)
    public void fetchEmployeeByLocate() {
        String search = "우";
        List<Integer> locates = employeeRepository.locate_function(search);
        for (Integer locate : locates) {
            System.out.println("LOCATE firstName : " + locate);
        }
        /**
         * [Hibernate]
         *     select
         *         locate(?, e1_0.first_name)
         *     from
         *         employee e1_0
         * 2024-08-05T00:54:30.629+09:00 TRACE 29022 --- [           main] org.hibernate.orm.jdbc.bind              : binding parameter (1:VARCHAR) <- [우]
         * LOCATE firstName : 2
         * LOCATE firstName : 0
         */
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByLower() {
        List<String> lower = employeeRepository.lower_function();
        for (String name : lower) {
            System.out.println("LOWER lastName : " + name);
        }
        /**
         * [Hibernate]
         *     select
         *         lower(e1_0.last_name)
         *     from
         *         employee e1_0
         * LOWER lastName : 김
         * LOWER lastName : 박
         */
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeByUpper() {
        List<String> upper = employeeRepository.upper_function();
        for (String name : upper) {
            System.out.println("UPPER lastName : " + name);
        }
        /**
         * [Hibernate]
         *     select
         *         upper(e1_0.last_name)
         *     from
         *         employee e1_0
         * UPPER lastName : 김
         * UPPER lastName : 박
         */
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeBySqrt() {
        List<Double> sqrt = employeeRepository.sqrt_function();
        for (Double salary : sqrt) {
            System.out.println("SQRT salary : " + salary);
        }
        /**
         * [Hibernate]
         *     select
         *         sqrt(e1_0.salary)
         *     from
         *         employee e1_0
         * SQRT salary : 31.622776601683793
         * SQRT salary : 31.622776601683793
         */
    }

    @Transactional(readOnly = true)
    public void fetchEmployeeBySubstring() {
        List<String> substrings = employeeRepository.substring_function();
        for (String name : substrings) {
            System.out.println("SUBSTRING firstName : " + name);
        }
        /**
         * [Hibernate]
         *     select
         *         substring(e1_0.first_name, 2, 2)
         *     from
         *         employee e1_0
         * SUBSTRING firstName : 우
         * SUBSTRING firstName : 원
         */
    }

    /**
     * TRIM (BOTH || LEADING || TRAILING) 함수
     * BOTH : 양쪽에서 제거
     * LEADING : 왼쪽에서 제거
     * TRAILING : 오른쪽에서 제거
     */
    @Transactional(readOnly = true)
    public void fetchEmployeeByTrim() {
        List<String> both = employeeRepository.trim1_function();
        List<String> leading = employeeRepository.trim2_function();
        List<String> trailing = employeeRepository.trim3_function();

        for (String name : both) {
            System.out.println("TRIM lastName : " + name);
        }

        for (String name : leading) {
            System.out.println("TRIM lastName : " + name);
        }

        for (String name : trailing) {
            System.out.println("TRIM lastName : " + name);
        }
        /**
         * [Hibernate]
         *     select
         *         trim(BOTH 'x' from 'xxhelloxx')
         * Found thread-bound EntityManager [SessionImpl(1272713136<open>)] for JPA transaction
         * Participating in existing transaction
         *
         * [Hibernate]
         *     select
         *         trim(LEADING '-' from '--example')
         * Found thread-bound EntityManager [SessionImpl(1272713136<open>)] for JPA transaction
         * Participating in existing transaction
         *
         * [Hibernate]
         *     select
         *         trim(TRAILING 'x' from 'helloxxx')
         *
         * TRIM lastName : hello
         * TRIM lastName : example
         * TRIM lastName : hello
         */
    }

}
