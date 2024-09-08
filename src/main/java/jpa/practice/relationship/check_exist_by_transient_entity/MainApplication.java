package jpa.practice.relationship.check_exist_by_transient_entity;


import jpa.practice.relationship.check_exist_by_transient_entity.entity.Book;
import jpa.practice.relationship.check_exist_by_transient_entity.service.BookstoreService;
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
            System.out.println("Find a book:");

            // this can come via a controller endpoint
            Book book = Book.builder()
                    .title("Carrie")
                    .genre("Horror")
                    .isbn("001-OG")
                    .price(23)
                    .build();

            boolean foundAnd = bookstoreService.existsBook1(book);
            System.out.println("Found (existsBook1): " + foundAnd + "\n");

            boolean foundOr = bookstoreService.existsBook2(book);
            System.out.println("Found (existsBook2): " + foundOr + "\n");

            boolean foundIgnorePath = bookstoreService.existsBook3(book);
            System.out.println("Found (existsBook3): " + foundIgnorePath + "\n");

            Book nullFieldBook = Book.builder()
                    .title("Carrie")
                    .genre(null)
                    .isbn("001-OG")
                    .price(23)
                    .build();
            boolean foundByIgnoreNull = bookstoreService.existsBook4(nullFieldBook);
            System.out.println("Found (existsBook4): " + foundByIgnoreNull + "\n");
        };
    }
}