package jpa.practice.relationship.logging_persistence_context.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.practice.relationship.logging_persistence_context.entity.Author;
import jpa.practice.relationship.logging_persistence_context.entity.Book;
import jpa.practice.relationship.logging_persistence_context.repository.AuthorRepository;
import jpa.practice.relationship.logging_persistence_context.repository.BookRepository;
import jpa.practice.relationship.logging_persistence_context.util.PersistenceContextUtil;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service
public class BookstoreService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookstoreService(AuthorRepository authorRepository, EntityManager entityManager, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.entityManager = entityManager;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void init() {
        // Author 데이터 초기화
        Author author1 = new Author();
        author1.setId(1L);
        author1.setAge(23);
        author1.setName("Mark Janel");
        author1.setGenre("Anthology");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setAge(43);
        author2.setName("Olivia Goy");
        author2.setGenre("Horror");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setId(3L);
        author3.setAge(51);
        author3.setName("Quartis Young");
        author3.setGenre("Anthology");
        authorRepository.save(author3);

        Author author4 = new Author();
        author4.setId(4L);
        author4.setAge(34);
        author4.setName("Joana Nimar");
        author4.setGenre("History");
        authorRepository.save(author4);

        // Book 데이터 초기화
        Book book1 = new Book();
        book1.setId(1L);
        book1.setIsbn("001-JN");
        book1.setTitle("A History of Ancient Prague");
        book1.setAuthor(author4);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbn("002-JN");
        book2.setTitle("A People's History");
        book2.setAuthor(author4);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setId(3L);
        book3.setIsbn("001-MJ");
        book3.setTitle("The Beatles Anthology");
        book3.setAuthor(author1);
        bookRepository.save(book3);

        Book book4 = new Book();
        book4.setId(4L);
        book4.setIsbn("001-OG");
        book4.setTitle("Carrie");
        book4.setAuthor(author2);
        bookRepository.save(book4);

        entityManager.flush();
        entityManager.clear();
    }


    @Transactional
    public void sqlOperations() {
        /**
         * -----------------------------------------------------
         * Total number of managed entities: 0
         * Total number of collection entries: 0
         * -----------------------------------------------------
         */
        PersistenceContextUtil.briefOverviewOfPersistentContextContent(entityManager);

        Author author = authorRepository.findByName("Joana Nimar");

        /**
         * -----------------------------------------------------
         * Total number of managed entities: 1
         * Total number of collection entries: 1
         *
         * Entities by key:
         * EntityKey[jpa.practice.relationship.logging_persistence_context.entity.Author#4]: Author{id=4, name=Joana Nimar, genre=History, age=34}
         *
         * Status and hydrated state:
         * Entity name: jpa.practice.relationship.logging_persistence_context.entity.Author | Status: MANAGED | State: [34, [Book{id=1, title=A History of Ancient Prague, isbn=001-JN}, Book{id=2, title=A People's History, isbn=002-JN}], History, Joana Nimar]
         *
         * Collection entries:
         * Key:[Book{id=1, title=A History of Ancient Prague, isbn=001-JN}, Book{id=2, title=A People's History, isbn=002-JN}], Value:CollectionEntry[jpa.practice.relationship.logging_persistence_context.entity.Author.books#4]
         * -----------------------------------------------------
         */
        PersistenceContextUtil.briefOverviewOfPersistentContextContent(entityManager);

        author.getBooks().get(0).setIsbn("not available");

        /**
         * -----------------------------------------------------
         * Total number of managed entities: 3
         * Total number of collection entries: 1
         *
         * Entities by key:
         * EntityKey[jpa.practice.relationship.logging_persistence_context.entity.Book#1]: Book{id=1, title=A History of Ancient Prague, isbn=not available}
         * EntityKey[jpa.practice.relationship.logging_persistence_context.entity.Book#2]: Book{id=2, title=A People's History, isbn=002-JN}
         * EntityKey[jpa.practice.relationship.logging_persistence_context.entity.Author#4]: Author{id=4, name=Joana Nimar, genre=History, age=34}
         *
         * Status and hydrated state:
         * Entity name: jpa.practice.relationship.logging_persistence_context.entity.Book | Status: MANAGED | State: [Author{id=4, name=Joana Nimar, genre=History, age=34}, 001-JN, A History of Ancient Prague]
         * Entity name: jpa.practice.relationship.logging_persistence_context.entity.Book | Status: MANAGED | State: [Author{id=4, name=Joana Nimar, genre=History, age=34}, 002-JN, A People's History]
         * Entity name: jpa.practice.relationship.logging_persistence_context.entity.Author | Status: MANAGED | State: [34, [Book{id=1, title=A History of Ancient Prague, isbn=not available}, Book{id=2, title=A People's History, isbn=002-JN}], History, Joana Nimar]
         *
         * Collection entries:
         * Key:[Book{id=1, title=A History of Ancient Prague, isbn=not available}, Book{id=2, title=A People's History, isbn=002-JN}], Value:CollectionEntry[jpa.practice.relationship.logging_persistence_context.entity.Author.books#4]
         * -----------------------------------------------------
         */
        PersistenceContextUtil.briefOverviewOfPersistentContextContent(entityManager);

        authorRepository.delete(author);
        authorRepository.flush();

        /**
         * -----------------------------------------------------
         * Total number of managed entities: 0
         * Total number of collection entries: 0
         * -----------------------------------------------------
         */
        PersistenceContextUtil.briefOverviewOfPersistentContextContent(entityManager);

        Author newAuthor = new Author();
        newAuthor.setName("Alicia Tom");
        newAuthor.setAge(38);
        newAuthor.setGenre("Anthology");

        Book book = new Book();
        book.setIsbn("001-AT");
        book.setTitle("The book of swords");

        newAuthor.addBook(book); // use addBook() helper

        authorRepository.saveAndFlush(newAuthor);

        /**
         * -----------------------------------------------------
         * Total number of managed entities: 2
         * Total number of collection entries: 1
         *
         * Entities by key:
         * EntityKey[jpa.practice.relationship.logging_persistence_context.entity.Author#5]: Author{id=5, name=Alicia Tom, genre=Anthology, age=38}
         * EntityKey[jpa.practice.relationship.logging_persistence_context.entity.Book#5]: Book{id=5, title=The book of swords, isbn=001-AT}
         *
         * Status and hydrated state:
         * Entity name: jpa.practice.relationship.logging_persistence_context.entity.Author | Status: MANAGED | State: [38, [Book{id=5, title=The book of swords, isbn=001-AT}], Anthology, Alicia Tom]
         * Entity name: jpa.practice.relationship.logging_persistence_context.entity.Book | Status: MANAGED | State: [Author{id=5, name=Alicia Tom, genre=Anthology, age=38}, 001-AT, The book of swords]
         *
         * Collection entries:
         * Key:[Book{id=5, title=The book of swords, isbn=001-AT}], Value:CollectionEntry[jpa.practice.relationship.logging_persistence_context.entity.Author.books#5]->[jpa.practice.relationship.logging_persistence_context.entity.Author.books#5]
         * -----------------------------------------------------
         */
        PersistenceContextUtil.briefOverviewOfPersistentContextContent(entityManager);
    }
}