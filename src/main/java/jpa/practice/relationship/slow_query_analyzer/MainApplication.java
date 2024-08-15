package jpa.practice.relationship.slow_query_analyzer;


import jpa.practice.relationship.slow_query_analyzer.service.BookstoreService;
import jpa.practice.relationship.slow_query_analyzer.slowquery_config.FunctionProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MainApplication {
    private final FunctionProvider functionProvider;
    private final BookstoreService bookstoreService;

    public MainApplication(FunctionProvider functionProvider, BookstoreService bookstoreService) {
        this.functionProvider = functionProvider;
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            functionProvider.addFunction();
            bookstoreService.init();
            bookstoreService.slowQuery();
        };
    }
}