package jpa.practice.relationship.onetomany.service;


import jakarta.persistence.Tuple;
import jpa.practice.relationship.onetomany.dto.v1.AuthorWithBooksDtoV1;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBookProjectionV2;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBooksDtoV2;
import jpa.practice.relationship.onetomany.dto.v2.BookDtoV2;
import jpa.practice.relationship.onetomany.dto.v3.AuthorWithBooksDtoV3;
import jpa.practice.relationship.onetomany.entity.Author;
import jpa.practice.relationship.onetomany.entity.Book;
import jpa.practice.relationship.onetomany.repository.AuthorRepository;
import jpa.practice.relationship.onetomany.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookstoreService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorBookTransformer authorBookTransformer;

    public BookstoreService(AuthorRepository authorRepository, BookRepository bookRepository, AuthorBookTransformer authorBookTransformer) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorBookTransformer = authorBookTransformer;
    }

    @Transactional
    public void init() {
        // Add author
        Author author = new Author("Alicia Tom", "Anthology", 38);
        Author author2 = new Author("Micheal", "Social", 33);
        Author author3 = new Author("Hanson", "Social", 33);
        authorRepository.saveAllAndFlush(List.of(author, author2, author3));

        // Add book
        Book book = new Book("title", "isbn");
        book.setAuthor(author);
        Book book2 = new Book("title2", "isbn2");
        book2.setAuthor(author);
        Book book3 = new Book("title3", "isbn3");
        book3.setAuthor(author);
        Book book4 = new Book("title4", "isbn4");
        book4.setAuthor(author2);
        Book book5 = new Book("title5", "isbn5");
        book5.setAuthor(author2);
        Book book6 = new Book("title6", "isbn6");
        book6.setAuthor(author3);
        Book book7 = new Book("title7", "isbn7");
        book7.setAuthor(author3);
        Book book8 = new Book("title8", "isbn8");
        bookRepository.saveAllAndFlush(List.of(book, book2, book3, book4, book5, book6, book7, book8));
    }

    @Transactional(readOnly = true)
    public void fetchAuthorWithBooks() {
        Author author = authorRepository.fetchWithBooksById(1L);
        System.out.println("Author: " + author);

        /**
         * begin
         * Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@25cef4b3]
         * Found thread-bound EntityManager [SessionImpl(463272985<open>)] for JPA transaction
         * Participating in existing transaction
         * [Hibernate]
         *     select
         *         a1_0.id,
         *         a1_0.age,
         *         b1_0.author_id,
         *         b1_0.id,
         *         b1_0.isbn,
         *         b1_0.title,
         *         a1_0.genre,
         *         a1_0.name
         *     from
         *         author a1_0
         *     join
         *         book b1_0
         *             on a1_0.id=b1_0.author_id
         *     where
         *         a1_0.id=?
         * binding parameter (1:BIGINT) <- [1]
         *
         * Author: Author{id=1, name='Alicia Tom', genre='Anthology', age=38}
         * Initiating transaction commit
         * Committing JPA transaction on EntityManager [SessionImpl(463272985<open>)]
         * committing
         */
    }

    @Transactional(readOnly = true)
    public void fetchAuthorsWithBooksV1(){
        AuthorWithBooksDtoV1 authorWithBooksDtoV1 = authorBookTransformer.toAuthorWithBooksDtoV1(authorRepository.fetchWithBooksByIdDtoV1(1L));
        System.out.println("AuthorWithBookDtoV1: " + authorWithBooksDtoV1);
        /**
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
         *         author a1_0
         *     left join
         *         book b1_0
         *             on a1_0.id=b1_0.author_id
         *     where
         *         a1_0.id=?
         */

    }

    @Transactional(readOnly = true)
    public void fetchAuthorsWithBooksV2(){
        List<AuthorWithBookProjectionV2> authorWithBookDtoV2 = authorRepository.fetchWithBooksProjectionByIdV2(1L);
        AuthorWithBooksDtoV2 result =  authorBookTransformer.toAuthorWithBooksDtoV2(authorWithBookDtoV2);
        result.books().stream()
                        .map(BookDtoV2::getTitle)
                        .forEach(System.out::println);
        System.out.println("AuthorWithBookDtoV1: " + authorWithBookDtoV2.get(0).getAuthor());
        System.out.println("AuthorWithBookDtoV1: " + authorWithBookDtoV2.get(0).getBook());
        /**
         * [Hibernate]
         *     select
         *         a1_0.id,
         *         a1_0.age,
         *         a1_0.genre,
         *         a1_0.name,
         *         b1_0.id,
         *         b1_0.author_id,
         *         b1_0.isbn,
         *         b1_0.title
         *     from
         *         author a1_0
         *     left join
         *         book b1_0
         *             on a1_0.id=b1_0.author_id
         *     where
         *         a1_0.id=?
         */
    }


    @Transactional(readOnly = true)
    public void fetchAuthorsWithBooksTupleV3(){
        List<Tuple> authorWithBooksTuple = authorRepository.fetchWithBooksTupleByIdV3(1L);
        List<AuthorWithBooksDtoV3> result = authorBookTransformer.toAuthorWithBooksDtoV3(authorWithBooksTuple);
        result.forEach(System.out::println);
        /**
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
         *         author a1_0
         *     left join
         *         book b1_0
         *             on a1_0.id=b1_0.author_id
         *     where
         *         a1_0.id=?
         */
    }

}
