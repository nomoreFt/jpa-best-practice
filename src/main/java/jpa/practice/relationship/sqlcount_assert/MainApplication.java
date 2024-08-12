package jpa.practice.relationship.sqlcount_assert;


import jpa.practice.relationship.sqlcount_assert.config.FunctionProvider;
import jpa.practice.relationship.sqlcount_assert.repository.AuthorRepository;
import jpa.practice.relationship.sqlcount_assert.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
        };
    }
}