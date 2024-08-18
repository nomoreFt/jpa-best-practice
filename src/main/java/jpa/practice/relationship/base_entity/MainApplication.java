package jpa.practice.relationship.base_entity;


import jpa.practice.relationship.base_entity.service.BookstoreService;
import jpa.practice.relationship.jpql_basic_function.service.EmployeeDateService;
import jpa.practice.relationship.jpql_basic_function.service.EmployeeService;
import jpa.practice.relationship.jpql_basic_function.service.EmployeeSpecialService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class MainApplication {
    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            System.out.println("Register new author ...");
            bookstoreService.registerAuthor();

            Thread.sleep(5000);

            System.out.println("Update an author ...");
            bookstoreService.updateAuthor();

            Thread.sleep(5000);
            System.out.println("Update books of an author ...");
            bookstoreService.updateBooks();
        };
    }
}