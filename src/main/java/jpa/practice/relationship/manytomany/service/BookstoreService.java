package jpa.practice.relationship.manytomany.service;


import jakarta.persistence.Tuple;
import jpa.practice.relationship.manytomany.dto.v1.AuthorBookDtoV1;
import jpa.practice.relationship.manytomany.dto.v1.AuthorWithBooksDtoV1;
import jpa.practice.relationship.manytomany.dto.v2.AuthorBookDtoV2;
import jpa.practice.relationship.manytomany.dto.v2.AuthorWithBooksDtoV2;
import jpa.practice.relationship.manytomany.dto.v3.AuthorWithBooksDtoV3;
import jpa.practice.relationship.manytomany.entity.Author;
import jpa.practice.relationship.manytomany.entity.AuthorBook;
import jpa.practice.relationship.manytomany.entity.Book;
import jpa.practice.relationship.manytomany.repository.AuthorBookRepository;
import jpa.practice.relationship.manytomany.repository.AuthorRepository;
import jpa.practice.relationship.manytomany.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookstoreService {
    private final AuthorBookRepository authorBookRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorBookTransformer authorBookTransformer;

    public BookstoreService(AuthorBookRepository authorBookRepository, AuthorRepository authorRepository, BookRepository bookRepository, AuthorBookTransformer authorBookTransformer) {
        this.authorBookRepository = authorBookRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;

        this.authorBookTransformer = authorBookTransformer;
    }

    @Transactional
    public void addAuthorAndBook() {
        // Add author
        Author author = new Author("Alicia Tom", "Anthology", 38);
        authorRepository.save(author);

        // Add book
        Book book = new Book("title", "isbn");
        bookRepository.save(book);

        Book book2 = new Book("title2", "isbn2");
        bookRepository.save(book2);

        // Add author and book
        AuthorBook authorBook = new AuthorBook(author, book);
        AuthorBook authorBook2 = new AuthorBook(author, book2);
        authorBookRepository.saveAndFlush(authorBook);
        authorBookRepository.saveAndFlush(authorBook2);

        System.out.println("Author: " + author);
        System.out.println("Book: " + book);
    }

    @Transactional(readOnly = true)
    public void findAuthorAndBooks() {
        authorBookRepository.findAll().forEach(authorBook -> {
            System.out.println("Author: " + authorBook.getAuthor());
            System.out.println("Book: " + authorBook.getBook());
        });
        }


    @Transactional(readOnly = true)
    public void fetchAllAuthorsAndBooks() {
        List<AuthorBook> authorBooks = authorBookRepository.fetchAll();
        //authorBooks에서 author만 모으기
        Map<Author, List<Book>> authorBooksMap = authorBooks.stream()
                .collect(Collectors.groupingBy(AuthorBook::getAuthor,
                        Collectors.mapping(AuthorBook::getBook, Collectors.toList())));
        System.out.println(authorBooksMap);
        System.out.println(authorBooks);
    }




    @Transactional(readOnly = true)
    public void fetchAuthorsAndBooksDTOV1() {
        List<AuthorBookDtoV1> byViaDTO = authorBookRepository.findByViaDTOV1();
        List<AuthorWithBooksDtoV1> authorWithBooksDtoV1s = authorBookTransformer.transformV1(byViaDTO);

        System.out.println(authorWithBooksDtoV1s);

        /**
         * begin
         * Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@9214725]
         * Found thread-bound EntityManager [SessionImpl(1268982338<open>)] for JPA transaction
         * Participating in existing transaction
         * [Hibernate]
         *     select
         *         a1_0.id,
         *         a1_0.name,
         *         a1_0.genre,
         *         a1_0.age,
         *         b1_0.id,
         *         b1_0.title,
         *         b1_0.isbn
         *     from
         *         author_book ab1_0
         *     join
         *         author a1_0
         *             on a1_0.id=ab1_0.author_id
         *     join
         *         book b1_0
         *             on b1_0.id=ab1_0.book_id
         *
         * [AuthorWithBooksDtoV1[id=1, name=Mark Janel, genre=Anthology, age=23, book=[BookDtoV1[bookId=1, bookTitle=Lost book], BookDtoV1[bookId=2, bookTitle=Lost2 book]]], AuthorWithBooksDtoV1[id=2, name=Mark Janel2, genre=Anthology2, age=40, book=[BookDtoV1[bookId=1, bookTitle=Lost book], BookDtoV1[bookId=3, bookTitle=Lost2 book], BookDtoV1[bookId=4, bookTitle=Lost2 book]]]]
         */
    }

    @Transactional(readOnly = true)
    public void fetchAuthorsAndBooksDTOV2() {
        List<AuthorBookDtoV2> byViaDTOV2 = authorBookRepository.findByViaDTOV2();
        List<AuthorWithBooksDtoV2> transform = authorBookTransformer.transformV2(byViaDTOV2);

/**
 * begin
 * Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@3fb920d9]
 * Found thread-bound EntityManager [SessionImpl(1423372658<open>)] for JPA transaction
 * Participating in existing transaction
 * [Hibernate]
 *     select
 *         a1_0.id,
 *         a1_0.age,
 *         a1_0.genre,
 *         a1_0.name,
 *         b1_0.id,
 *         b1_0.isbn,
 *         b1_0.title
 *     from
 *         author_book ab1_0
 *     join
 *         author a1_0
 *             on a1_0.id=ab1_0.author_id
 *     join
 *         book b1_0
 *             on b1_0.id=ab1_0.book_id
 * [Hibernate]
 *     select
 *         a1_0.book_id,
 *         a1_0.author_id,
 *         a1_0.published_on
 *     from
 *         author_book a1_0
 *     where
 *         a1_0.book_id=?
 * binding parameter (1:BIGINT) <- [1]
 *
 * [Hibernate]
 *     select
 *         a1_0.book_id,
 *         a1_0.author_id,
 *         a1_0.published_on
 *     from
 *         author_book a1_0
 *     where
 *         a1_0.book_id=?
 * binding parameter (1:BIGINT) <- [2]
 * [Hibernate]
 *     select
 *         a1_0.book_id,
 *         a1_0.author_id,
 *         a1_0.published_on
 *     from
 *         author_book a1_0
 *     where
 *         a1_0.book_id=?
 * binding parameter (1:BIGINT) <- [3]
 *
 * [Hibernate]
 *     select
 *         a1_0.book_id,
 *         a1_0.author_id,
 *         a1_0.published_on
 *     from
 *         author_book a1_0
 *     where
 *         a1_0.book_id=?
 * binding parameter (1:BIGINT) <- [4]
 *
 * [AuthorWithBooksDtoV2[
 * id=1, name=Mark Janel, genre=Anthology, age=23,
 * book=[Book{id=1, title='Lost book', isbn='001-null', authors=[AuthorBook{id=AuthorBookId{authorId=1, bookId=1}, publishedOn=2024-07-25 23:38:18.081193}, AuthorBook{id=AuthorBookId{authorId=2, bookId=1}, publishedOn=2024-07-25 23:38:18.081193}]}, Book{id=2, title='Lost2 book', isbn='002-null', authors=[AuthorBook{id=AuthorBookId{authorId=1, bookId=2}, publishedOn=2024-07-25 23:38:18.081193}]}]], AuthorWithBooksDtoV2[id=2, name=Mark Janel2, genre=Anthology2, age=40, book=[Book{id=1, title='Lost book', isbn='001-null', authors=[AuthorBook{id=AuthorBookId{authorId=1, bookId=1}, publishedOn=2024-07-25 23:38:18.081193}, AuthorBook{id=AuthorBookId{authorId=2, bookId=1}, publishedOn=2024-07-25 23:38:18.081193}]}, Book{id=3, title='Lost2 book', isbn='003-null', authors=[AuthorBook{id=AuthorBookId{authorId=2, bookId=3}, publishedOn=2024-07-25 23:38:18.081193}]}, Book{id=4, title='Lost2 book', isbn='004-null', authors=[AuthorBook{id=AuthorBookId{authorId=2, bookId=4}, publishedOn=2024-07-25 23:38:18.081193}]}]]]
 */
    }

    @Transactional(readOnly = true)
    public void fetchAuthorsAndBooksTupleV3() {
        List<Tuple> byViaTupleV3 = authorBookRepository.findByViaTupleV3();
        List<AuthorWithBooksDtoV3> authorWithBooksDtoV3s = authorBookTransformer.transformV3(byViaTupleV3);

        System.out.println(authorWithBooksDtoV3s);

        /**
         * begin
         * Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@4cd13fad]
         * Found thread-bound EntityManager [SessionImpl(1355471790<open>)] for JPA transaction
         * Participating in existing transaction
         * [Hibernate]
         *     select
         *         a1_0.id,
         *         a1_0.name,
         *         a1_0.genre,
         *         a1_0.age,
         *         b1_0.id,
         *         b1_0.title,
         *         b1_0.isbn
         *     from
         *         author_book ab1_0
         *     join
         *         author a1_0
         *             on a1_0.id=ab1_0.author_id
         *     join
         *         book b1_0
         *             on b1_0.id=ab1_0.book_id
         * [AuthorWithBooksDtoV3[id=1, name=Mark Janel, genre=Anthology, age=23, book=[BookDtoV3[bookId=1, bookTitle=Lost book, bookIsbn=001-null], BookDtoV3[bookId=2, bookTitle=Lost2 book, bookIsbn=002-null]]]
         * , AuthorWithBooksDtoV3[id=2, name=Mark Janel2, genre=Anthology2, age=40, book=[BookDtoV3[bookId=1, bookTitle=Lost book, bookIsbn=001-null], BookDtoV3[bookId=3, bookTitle=Lost2 book, bookIsbn=003-null], BookDtoV3[bookId=4, bookTitle=Lost2 book, bookIsbn=004-null]]]]
         * Initiating transaction commit
         * Committing JPA transaction on EntityManager [SessionImpl(1355471790<open>)]
         * committing
         */
    }
}
