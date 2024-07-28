package jpa.practice.relationship.onetomany;



import jpa.practice.relationship.onetomany.service.BookstoreService;
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
            //bookstoreService.fetchAuthorWithBooks();
            //bookstoreService.fetchAuthorsWithBooksV1();
            //bookstoreService.fetchAuthorsWithBooksV2();
            bookstoreService.fetchAuthorsWithBooksTupleV3();
        };
    }
}