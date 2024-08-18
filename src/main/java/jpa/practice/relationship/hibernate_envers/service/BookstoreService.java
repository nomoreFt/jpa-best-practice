package jpa.practice.relationship.hibernate_envers.service;


import jakarta.persistence.EntityManager;
import jpa.practice.relationship.hibernate_envers.entity.Book;
import jpa.practice.relationship.hibernate_envers.entity.Author;
import jpa.practice.relationship.hibernate_envers.repository.AuthorRepository;
import jpa.practice.relationship.hibernate_envers.repository.BookRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BookstoreService {
    private static final Logger logger = Logger.getLogger(BookstoreService.class.getName());

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final EntityManager em;

    public BookstoreService(AuthorRepository authorRepository, BookRepository bookRepository, EntityManager em) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.em = em;
    }

    @Transactional
    public void registerAuthor() {
        Author a1 = Author.builder()
                .name("Quartis Young")
                .genre("Anthology")
                .age(34)
                .build();

        Author a2 = Author.builder()
                .name("Mark Janel")
                .genre("Anthology")
                .age(23)
                .build();

        jpa.practice.relationship.hibernate_envers.entity.Book b1 = Book.builder()
                .isbn("001")
                .title("The Beatles Anthology")
                .build();

        Book b2 = Book.builder()
                .isbn("002")
                .title("A People's Anthology")
                .build();
        Book b3 = Book.builder()
                .isbn("003")
                .title("Anthology Myths")
                .build();

        a1.addBook(b1);
        a1.addBook(b2);
        a2.addBook(b3);

        authorRepository.save(a1);
        authorRepository.save(a2);
    }

    @Transactional
    public void updateAuthor(int age) {
        Author author = authorRepository.findByName("Mark Janel");

        author.setAge(age);
    }

    @Transactional
    public void updateBooks() {
        Author author = authorRepository.findByName("Quartis Young");
        List<Book> books = author.getBooks();

        for (Book book : books) {
            book.setIsbn("not available");
        }
    }

    @Transactional(readOnly = true)
    public void queryEntityHistory() {
        AuditReader reader = AuditReaderFactory.get(em);

        AuditQuery queryAtRev = reader.createQuery().forEntitiesAtRevision(Book.class, 3);
        System.out.println("Get all Book instances modified at revision #3:");
        System.out.println(queryAtRev.getResultList());

        AuditQuery queryOfRevs = reader.createQuery().forRevisionsOfEntity(Book.class, true, true);
        System.out.println("\nGet all Book instances in all their states that were audited:");
        System.out.println(queryOfRevs.getResultList());
    }
}