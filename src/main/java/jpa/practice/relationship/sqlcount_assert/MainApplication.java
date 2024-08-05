package jpa.practice.relationship.sqlcount_assert;


import com.vladmihalcea.sql.SQLStatementCountValidator;
import jpa.practice.relationship.onetoone.service.MemberService;
import jpa.practice.relationship.sqlcount_assert.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertDeleteCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertUpdateCount;

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

            //SQLStatementCountValidator.reset();
            bookstoreService.updateAuthor();
            // at this point there is no transaction running
            // there are 3 statements
/*            assertSelectCount(2);
            assertUpdateCount(1);
            assertInsertCount(0);
            assertDeleteCount(0);*/
        };
    }
}