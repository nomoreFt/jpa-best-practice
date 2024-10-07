package jpa.practice.relationship.testProfile;


import jpa.practice.relationship.sqlcount_assert.service.BookstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {
    private final TestUseCase testUseCase;

    public MainApplication(TestUseCase testUseCase) {
        this.testUseCase = testUseCase;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            testUseCase.test();
        };
    }
}