package jpa.practice.relationship.jpql_basic_function.service;

import jpa.practice.relationship.jpql_basic_function.entity.Employee;
import jpa.practice.relationship.jpql_basic_function.entity.collection.Book;
import jpa.practice.relationship.jpql_basic_function.entity.collection.Novel;
import jpa.practice.relationship.jpql_basic_function.entity.collection.TextBook;
import jpa.practice.relationship.jpql_basic_function.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeSpecialService {

    private final EmployeeRepository employeeRepository;

    public EmployeeSpecialService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * ElementCollection의 @OrderColumn이 선언된 경우, INDEX() 함수를 사용하여 특정 인덱스의 요소를 조회할 수 있다.
     */

    @Transactional(readOnly = true)
    public void findEmployeeByIndex() {
        List<Book> favoriteBooksByIndex = employeeRepository.index_function(1);
        favoriteBooksByIndex.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         fb1_0.author,
         *         fb1_0.title
         *     from
         *         employee e1_0
         *     join
         *         employee_favorite_books fb1_0
         *             on e1_0.id=fb1_0.employee_id
         *     where
         *         fb1_0.book_order=?
         * binding parameter (1:INTEGER) <- [1]
         * Book{title='Spring Security', author='hyunwooKim'}
         * Book{title='Spring Boot', author='jiwonPark'}
         */
    }

    /**
     * ElementCollection의 @MapKeyColumn이 선언된 경우
     * ,KEY() 함수를 사용하여 특정 키의 요소를 조회할 수 있다.
     */

    @Transactional(readOnly = true)
    public void findEmployeeByKey() {
        List<String> courceDescriptionByKey = employeeRepository.key_function("Backend");
        courceDescriptionByKey.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         c1_0.course_name
         *     from
         *         employee e1_0
         *     join
         *         employee_courses c1_0
         *             on e1_0.id=c1_0.employee_id
         *     where
         *         c1_0.courses_key=?
         *
         * binding parameter (1:VARCHAR) <- [Backend]
         * Spring Boot Framework
         * Spring Framework
         */
    }

    @Transactional(readOnly = true)
    public void findEmployeeByFavoriteBooksSize() {
        List<Employee> employeesByFavoriteBooksSize = employeeRepository.collectionSize_function(3);
        employeesByFavoriteBooksSize.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         e1_0.id,
         *         e1_0.first_name,
         *         e1_0.gender,
         *         e1_0.last_name,
         *         e1_0.salary,
         *         e1_0.start_date,
         *         e1_0.start_date_time,
         *         e1_0.start_instant,
         *         e1_0.start_time,
         *         e1_0.status
         *     from
         *         employee e1_0
         *     where
         *         (
         *             select
         *                 count(1)
         *             from
         *                 employee_favorite_books fb1_0
         *             where
         *                 e1_0.id=fb1_0.employee_id
         *         )>=?
         * binding parameter (1:INTEGER) <- [3]
         * Employee{id=1, firstName=현우, lastName=김, startDate=2021-01-01, startTime=09:00, startDateTime=2021-01-01T09:00, startInstant=2021-01-01T00:00:00Z, salary=1000.0
         *
         **/
         }


    @Transactional(readOnly = true)
    public void findEmployeeByCoursesNotEmpty() {
        List<Employee> notEmptyFunction = employeeRepository.isNotEmpty_function();
        notEmptyFunction.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         e1_0.id,
         *         e1_0.first_name,
         *         e1_0.gender,
         *         e1_0.last_name,
         *         e1_0.salary,
         *         e1_0.start_date,
         *         e1_0.start_date_time,
         *         e1_0.start_instant,
         *         e1_0.start_time,
         *         e1_0.status
         *     from
         *         employee e1_0
         *     where
         *         exists(select
         *             1
         *         from
         *             employee_courses c1_0
         *         where
         *             e1_0.id=c1_0.employee_id)
         * Employee{id=1, firstName=현우, lastName=김, startDate=2021-01-01, startTime=09:00, startDateTime=2021-01-01T09:00, startInstant=2021-01-01T00:00:00Z, salary=1000.0
         * Employee{id=2, firstName=지원, lastName=박, startDate=2021-01-01, startTime=09:00, startDateTime=2021-01-01T09:00, startInstant=2021-01-01T00:00:00Z, salary=1000.0
         */
    }

    @Transactional(readOnly = true)
    public void findEmployeeByCoursesMemberOf() {
        List<Employee> memberOfFunction = employeeRepository.memberOf_function(Novel.builder()
                .title("Spring Security")
                .author("hyunwooKim")
                .genre("IT")
                .build());
        memberOfFunction.forEach(System.out::println);

        /**
         * [Hibernate]
         *     select
         *         e1_0.id,
         *         e1_0.first_name,
         *         e1_0.gender,
         *         e1_0.last_name,
         *         e1_0.salary,
         *         e1_0.start_date,
         *         e1_0.start_date_time,
         *         e1_0.start_instant,
         *         e1_0.start_time,
         *         e1_0.status
         *     from
         *         employee e1_0
         *     where
         *         (
         *             ?, ?
         *         ) in (select
         *             fb1_0.author, fb1_0.title
         *         from
         *             employee_favorite_books fb1_0
         *         where
         *             e1_0.id=fb1_0.employee_id)
         * binding parameter (1:VARCHAR) <- [hyunwooKim]
         * binding parameter (2:VARCHAR) <- [Spring Security]
         * Employee{id=1, firstName=현우, lastName=김, startDate=2021-01-01, startTime=09:00, startDateTime=2021-01-01T09:00, startInstant=2021-01-01T00:00:00Z, salary=1000.0
         */
    }




}
