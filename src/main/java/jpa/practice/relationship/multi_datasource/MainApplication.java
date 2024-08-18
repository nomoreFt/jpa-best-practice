package jpa.practice.relationship.multi_datasource;


import jpa.practice.relationship.multi_datasource.service.BookStoreService;
import jpa.practice.relationship.useful_transient.service.OrderService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class MainApplication {
    private final BookStoreService bookStoreService;

    public MainApplication(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            bookStoreService.persistAuthor();
            bookStoreService.persistBook();

            bookStoreService.readAuthor();
            bookStoreService.readBook();
        };
    }
}