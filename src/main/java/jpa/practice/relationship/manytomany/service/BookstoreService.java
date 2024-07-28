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
import jpa.practice.relationship.manytomany.repository.AuthorBookTransformerImpl;
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
    private final AuthorBookTransformerImpl authorBookTransformer;

    public BookstoreService(AuthorBookRepository authorBookRepository, AuthorRepository authorRepository, BookRepository bookRepository, AuthorBookTransformerImpl authorBookTransformer) {
        this.authorBookRepository = authorBookRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;

        this.authorBookTransformer = authorBookTransformer;
    }

    /**
     * IDENTITY라 개별 INSERT가 발생한다.
     * SEQUENCE방식 사용시 BATCH INSERT로 개선 가능하다.
     * 근데 대량 INSERT 상황이 그렇게 자주 발생하는지는 고민해봐야 한다.
     * 보통 READ or UPDATE가 주로 발생한다고 생각한다.
     * 혹시 대량의 상황이 발생할 경우 JDBC를 이용할 것 같다.
     */
    @Transactional
    public void init() {
        // Add author
        Author author = new Author("Alicia Tom", "Anthology", 38);
        Author author2 = new Author("Micheal", "Social", 33);
        Author author3 = new Author("Hanson", "Social", 33);
        authorRepository.saveAllAndFlush(List.of(author, author2, author3));

        // Add book
        Book book = new Book("title", "isbn");
        Book book2 = new Book("title2", "isbn2");
        Book book3 = new Book("title3", "isbn3");
        Book book4 = new Book("title4", "isbn4");
        Book book5 = new Book("title5", "isbn5");
        Book book6 = new Book("title6", "isbn6");
        Book book7 = new Book("title7", "isbn7");
        Book book8 = new Book("title8", "isbn8");
        bookRepository.saveAllAndFlush(List.of(book, book2, book3, book4, book5, book6, book7, book8));

        // Add author and book
        AuthorBook authorBook = new AuthorBook(author, book);
        AuthorBook authorBook2 = new AuthorBook(author, book2);

        AuthorBook authorBook3 = new AuthorBook(author2, book3);
        AuthorBook authorBook4 = new AuthorBook(author2, book4);
        AuthorBook authorBook5 = new AuthorBook(author2, book5);
        AuthorBook authorBook6 = new AuthorBook(author2, book);

        AuthorBook authorBook7 = new AuthorBook(author3, book6);
        AuthorBook authorBook8 = new AuthorBook(author3, book7);
        AuthorBook authorBook9 = new AuthorBook(author3, book8);
        AuthorBook authorBook10 = new AuthorBook(author3, book2);
        authorBookRepository.saveAllAndFlush(List.of(authorBook, authorBook2, authorBook3, authorBook4, authorBook5, authorBook6,
                authorBook7, authorBook8, authorBook9, authorBook10));
    }


    /**
     * fetch join을 써서 한 번에 가져와 추가적인 SELECT가 없을 줄 알았는데 결국 추가적인 SELECT가 발생한다.
     * 수정할 일이 아니라면 DTO,Tuple로 가져와 1번의 쿼리만 발생시키고 자바에서 처리하는 것이 좋을 것 같다.
     * hibernate의 default_batch_fetch_size 를 설정하면 추가적인 SELECT가 줄어들 수 있다.
     */
    @Transactional(readOnly = true)
    public void fetchAllAuthorsAndBooks() {
        List<AuthorBook> all = authorBookRepository.fetchAll();
        Map<Author, List<Book>> collect = all.stream()
                .collect(Collectors.groupingBy(AuthorBook::getAuthor,
                        Collectors.mapping(AuthorBook::getBook, Collectors.toList())));

        System.out.println(all.get(0).getAuthor());
        System.out.println(all.get(0).getBook());
        System.out.println(all.get(6).getAuthor());
        System.out.println(all.get(6).getBook());

        /**
         * begin
         * Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@2b551e7b]
         * Found thread-bound EntityManager [SessionImpl(1623105503<open>)] for JPA transaction
         * Participating in existing transaction
         *
         * [Hibernate]
         *     select
         *         ab1_0.author_id,
         *         ab1_0.book_id,
         *         a1_0.id,
         *         a1_0.age,
         *         a1_0.genre,
         *         a1_0.name,
         *         b1_0.id,
         *         b1_0.isbn,
         *         b1_0.title,
         *         ab1_0.published_on
         *     from
         *         author_book ab1_0
         *     join
         *         author a1_0
         *             on a1_0.id=ab1_0.author_id
         *     join
         *         book b1_0
         *             on b1_0.id=ab1_0.book_id
         *
         *
         * [Hibernate]
         *     select
         *         b1_0.author_id,
         *         b1_0.book_id,
         *         b1_0.published_on
         *     from
         *         author_book b1_0
         *     where
         *         b1_0.author_id in (?, ?, ?, ?, ?)
         * binding parameter (1:BIGINT) <- [1]
         * binding parameter (2:BIGINT) <- [2]
         * binding parameter (3:BIGINT) <- [3]
         * binding parameter (4:BIGINT) <- [null]
         * binding parameter (5:BIGINT) <- [null]
         *
         * Author{id=1, name='Alicia Tom', genre='Anthology', age=38, books=[AuthorBook{id=AuthorBookId{authorId=1, bookId=1}, publishedOn=2024-07-28 10:04:36.062}, AuthorBook{id=AuthorBookId{authorId=1, bookId=2}, publishedOn=2024-07-28 10:04:36.062}]}
         * [Hibernate]
         *     select
         *         a1_0.book_id,
         *         a1_0.author_id,
         *         a1_0.published_on
         *     from
         *         author_book a1_0
         *     where
         *         a1_0.book_id in (?, ?, ?, ?, ?)
         * binding parameter (1:BIGINT) <- [1]
         * binding parameter (2:BIGINT) <- [2]
         * binding parameter (3:BIGINT) <- [3]
         * binding parameter (4:BIGINT) <- [4]
         * binding parameter (5:BIGINT) <- [5]
         * Book{id=1, title='title', isbn='isbn', authors=[AuthorBook{id=AuthorBookId{authorId=1, bookId=1}, publishedOn=2024-07-28 10:04:36.062}, AuthorBook{id=AuthorBookId{authorId=2, bookId=1}, publishedOn=2024-07-28 10:04:36.062}]}
         * Author{id=3, name='Hanson', genre='Social', age=33, books=[AuthorBook{id=AuthorBookId{authorId=3, bookId=6}, publishedOn=2024-07-28 10:04:36.062}, AuthorBook{id=AuthorBookId{authorId=3, bookId=7}, publishedOn=2024-07-28 10:04:36.062}, AuthorBook{id=AuthorBookId{authorId=3, bookId=8}, publishedOn=2024-07-28 10:04:36.062}, AuthorBook{id=AuthorBookId{authorId=3, bookId=2}, publishedOn=2024-07-28 10:04:36.062}]}
         * [Hibernate]
         *     select
         *         a1_0.book_id,
         *         a1_0.author_id,
         *         a1_0.published_on
         *     from
         *         author_book a1_0
         *     where
         *         a1_0.book_id in (?, ?, ?, ?, ?)
         * binding parameter (1:BIGINT) <- [6]
         * binding parameter (2:BIGINT) <- [7]
         * binding parameter (3:BIGINT) <- [8]
         * binding parameter (4:BIGINT) <- [null]
         * binding parameter (5:BIGINT) <- [null]
         * Book{id=6, title='title6', isbn='isbn6', authors=[AuthorBook{id=AuthorBookId{authorId=3, bookId=6}, publishedOn=2024-07-28 10:04:36.062}]}
         *
         * Initiating transaction commit
         * Committing JPA transaction on EntityManager [SessionImpl(1623105503<open>)]
         * committing
         * Closing JPA EntityManager [SessionImpl(1623105503<open>)] after transaction
         * Closing JPA EntityManagerFactory for persistence unit 'default'
         * HikariPool-1 - Shutdown initiated...
         * HikariPool-1 - Before shutdown stats (total=10, active=0, idle=10, waiting=0)
         */

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
    public void fetchAuthorsAndBooksProjectionV2() {
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
