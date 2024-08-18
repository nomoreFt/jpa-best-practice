package jpa.practice.relationship.logging_persistence_context;


import jpa.practice.relationship.logging_persistence_context.service.BookstoreService;
import org.hibernate.annotations.processing.SQL;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
            bookstoreService.init();
            System.out.println("===========Executing SQL operations ...=======================");
            bookstoreService.sqlOperations();
        };
    }
}