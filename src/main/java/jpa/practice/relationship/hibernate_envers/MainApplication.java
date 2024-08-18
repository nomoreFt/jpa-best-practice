package jpa.practice.relationship.hibernate_envers;


import jpa.practice.relationship.hibernate_envers.service.BookstoreService;
import jpa.practice.relationship.hibernate_envers.service.RevisionService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    private final BookstoreService bookstoreService;
    private final RevisionService revisionService;

    public MainApplication(BookstoreService bookstoreService, RevisionService revisionService) {
        this.bookstoreService = bookstoreService;
        this.revisionService = revisionService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            System.out.println("\nQuery entity history ...");
            System.out.println("------------------------");
            bookstoreService.queryEntityHistory();

            System.out.println("\nRegister new author ...");
            System.out.println("-----------------------");
            bookstoreService.registerAuthor();

            //Thread.sleep(5000);

            System.out.println("\nUpdate an author ...");
            System.out.println("--------------------");
            bookstoreService.updateAuthor(45);
            bookstoreService.updateAuthor(47);

            //Thread.sleep(5000);
            System.out.println("\nUpdate books of an author ...");
            System.out.println("-----------------------------");
            bookstoreService.updateBooks();

            System.out.println("\nQuery entity history ...");
            System.out.println("------------------------");
            bookstoreService.queryEntityHistory();

            revisionService.queryEntityHistoryById();
            revisionService.queryEntityHistoryByAuthor();
            revisionService.queryLatestEntityHistory();
            revisionService.queryLatestEntityHistory2();

        };
    }
}