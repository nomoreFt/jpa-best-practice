package jpa.practice.relationship.manytomany;


import jpa.practice.relationship.manytomany.service.BookstoreService;
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
            //bookstoreService.fetchAuthorsAndBooksDTOV1();
            //bookstoreService.fetchAuthorsAndBooksDTOV2();
            bookstoreService.fetchAuthorsAndBooksTupleV3();
        };
    }
}