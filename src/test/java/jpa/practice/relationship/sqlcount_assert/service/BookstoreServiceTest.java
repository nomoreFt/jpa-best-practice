package jpa.practice.relationship.sqlcount_assert.service;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import jpa.practice.relationship.sqlcount_assert.config.BaseQueryTest;
import jpa.practice.relationship.sqlcount_assert.config.CleanUp;
import jpa.practice.relationship.sqlcount_assert.entity.Author;
import jpa.practice.relationship.sqlcount_assert.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookstoreServiceTest extends BaseQueryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookstoreService bookstoreService;
    @Autowired
    private CleanUp cleanUp;

    private final String AUTHOR_NAME = "Joana Nimar";
    private final String INITIAL_GENRE = "Fantasy";
    private final int INITIAL_AGE = 34;

    private Author createAndSaveAuthor() {
        Author author = Author.builder()
                .name(AUTHOR_NAME)
                .genre(INITIAL_GENRE)
                .age(INITIAL_AGE)
                .build();
        return authorRepository.saveAndFlush(author);
    }

    @BeforeEach
    void setUp() {
        cleanUp.all();
    }

    @Test
    @DisplayName("비효율적인 방법으로 author를 수정하면 2SELECT, 1UPDATE가 발생해야 한다.")
    void whenUpdateAuthorWithoutTransactional_thenTwoSelectAndOneUpdate() {
        // given
        Author author = createAndSaveAuthor();
        String updatedGenre = "History";
        int updatedAge = 35;

        // when
        initializeQueryCountTest();

        bookstoreService.updateAuthor_Bad(author.getId(), updatedGenre, updatedAge);

        // then
        assertQueryCount(2,1,0,0);

        // 값에 대한 Assertions
        Author updatedAuthor = authorRepository.findById(author.getId()).orElseThrow();
        Assertions.assertEquals(updatedGenre, updatedAuthor.getGenre());
        Assertions.assertEquals(updatedAge, updatedAuthor.getAge());
    }

    private static void assertQueryCount(int expectedSelectCount, int expectedUpdateCount, int expectedDeleteCount, int expectedInsertCount) {
        SQLStatementCountValidator.assertSelectCount(expectedSelectCount);
        SQLStatementCountValidator.assertUpdateCount(1);
        SQLStatementCountValidator.assertDeleteCount(0);
        SQLStatementCountValidator.assertInsertCount(0);
    }

    @Test
    @DisplayName("효율적인 방법으로 author를 수정하면 1SELECT, 1UPDATE가 발생해야 한다.")
    void whenUpdateAuthorWithTransactional_thenOneSelectAndOneUpdate() {
        // given
        Author author = createAndSaveAuthor();
        String updatedGenre = "History";
        int updatedAge = 35;

        // when
        initializeQueryCountTest();

        bookstoreService.updateAuthor_Good(author.getId(), updatedGenre, updatedAge);

        // then
        assertQueryCount(1,1,0,0);

        // 값에 대한 Assertions
        Author updatedAuthor = authorRepository.findById(author.getId()).orElseThrow();
        Assertions.assertEquals(updatedGenre, updatedAuthor.getGenre());
        Assertions.assertEquals(updatedAge, updatedAuthor.getAge());
    }
}