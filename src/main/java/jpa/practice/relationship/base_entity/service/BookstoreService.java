package jpa.practice.relationship.base_entity.service;

import jpa.practice.relationship.base_entity.entity.Author;
import jpa.practice.relationship.base_entity.entity.Book;
import jpa.practice.relationship.base_entity.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookstoreService {
    private final AuthorRepository authorRepository;

    public BookstoreService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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

        Book b1 = Book.builder()
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

    /**
     * [Hibernate]
     *     update
     *         author
     *     set
     *         age=?,
     *         created=?,
     *         created_by=?,
     *         genre=?,
     *         last_modified=?,
     *         last_modified_by=?,
     *         name=?
     *     where
     *         id=?
     */
    @Transactional
    public void updateAuthor() {
        Author author = authorRepository.findByName("Mark Janel");

        author.old(45);
    }

    /**
     * [Hibernate]
     *     update
     *         book
     *     set
     *         author_id=?,
     *         created=?,
     *         created_by=?,
     *         isbn=?,
     *         last_modified=?,
     *         last_modified_by=?,
     *         title=?
     *     where
     *         id=?
     */
    @Transactional
    public void updateBooks() {
        Author author = authorRepository.findByName("Quartis Young");
        List<Book> books = author.getBooks();

        for (Book book : books) {
            book.setIsbn("not available");
        }
    }
}