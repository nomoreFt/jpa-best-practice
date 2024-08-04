package jpa.practice.relationship.jpql_basic_function;


import jpa.practice.relationship.jpql_basic_function.service.EmployeeDateService;
import jpa.practice.relationship.jpql_basic_function.service.EmployeeService;
import jpa.practice.relationship.jpql_basic_function.service.EmployeeSpecialService;
import jpa.practice.relationship.useful_transient.service.OrderService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    private final EmployeeService employeeService;
    private final EmployeeDateService employeeDateService;
    private final EmployeeSpecialService employeeSpecialService;

    public MainApplication(EmployeeService employeeService, EmployeeDateService employeeDateService, EmployeeSpecialService employeeSpecialService) {
        this.employeeService = employeeService;
        this.employeeDateService = employeeDateService;
        this.employeeSpecialService = employeeSpecialService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            employeeService.init();
            /*employeeDateService.fetchEmployeeByDate();
            employeeDateService.fetchEmployeeByTime();
            employeeDateService.fetchEmployeeByDateTime();
            employeeDateService.fetchEmployeeByInstant();*/
            //employeeService.fetchEmployeeBySalaryDifferenceGreaterThan(1000.0);
            //employeeService.fetchEmployeeByStatusDescription("active");
            //employeeService.fetchEmployeeBySalaryNotNull();
            //employeeService.fetchEmployeeByConcat();
            //employeeService.fetchEmployeeByLength();
            //employeeService.fetchEmployeeByLocate();
            //employeeService.fetchEmployeeByLower();
            //employeeService.fetchEmployeeByUpper();
            //employeeService.fetchEmployeeBySqrt();
            //employeeService.fetchEmployeeBySubstring();
            //employeeService.fetchEmployeeByTrim();

            //employeeSpecialService.findEmployeeByIndex();
            //employeeSpecialService.findEmployeeByKey();
            //employeeSpecialService.findEmployeeByFavoriteBooksSize();
            //employeeSpecialService.findEmployeeByCoursesNotEmpty();
            //employeeSpecialService.findEmployeeByCoursesMemberOf();
        };
    }
}