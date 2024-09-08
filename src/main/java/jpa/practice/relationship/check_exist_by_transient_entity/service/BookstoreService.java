package jpa.practice.relationship.check_exist_by_transient_entity.service;


import jpa.practice.relationship.check_exist_by_transient_entity.entity.Book;
import jpa.practice.relationship.check_exist_by_transient_entity.repository.BookRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class BookstoreService {
    private final BookRepository bookRepository;

    public BookstoreService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     *     select
     *         1
     *     from
     *         book b1_0
     *     where
     *         b1_0.isbn=?
     *         and b1_0.author=?
     *         and b1_0.genre=?
     *         and b1_0.price=?
     *         and b1_0.title=?
     *     fetch
     *         first ? rows only
     */
    public boolean existsBook1(Book book) {
        Example<Book> bookExample = Example.of(book);
        return bookRepository.exists(bookExample);
    }

    /**
     *     select
     *         1
     *     from
     *         book b1_0
     *     where
     *         b1_0.isbn=?
     *         and b1_0.author=?
     *         and b1_0.title=?
     *     fetch
     *         first ? rows only
     */
    public boolean existsBook2(Book book) {
        Example<Book> bookExample = Example.of(book,
                ExampleMatcher.matchingAll().withIgnorePaths("genre", "price"));
        return bookRepository.exists(bookExample);
    }

    /**
     * [Hibernate]
     *     select
     *         1
     *     from
     *         book b1_0
     *     where
     *         b1_0.isbn=?
     *         or b1_0.author=?
     *         or b1_0.title=?
     *     fetch
     *         first ? rows only
     */
    public boolean existsBook3(Book book) {
        Example<Book> bookExample = Example.of(book,
                ExampleMatcher.matchingAny().withIgnorePaths("genre", "price"));
        return bookRepository.exists(bookExample);
    }

    /**
     * [Hibernate]
     *     select
     *         1
     *     from
     *         book b1_0
     *     where
     *         b1_0.price=?
     *         or b1_0.isbn=?
     *         or b1_0.title=?
     *     fetch
     *         first ? rows only
     *
     *         null인 속성 제외
     */
    public boolean existsBook4(Book book) {
        Example<Book> bookExample = Example.of(book,
                ExampleMatcher.matchingAny().withIgnoreNullValues());
        return bookRepository.exists(bookExample);
    }


}