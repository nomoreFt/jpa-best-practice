package jpa.practice.relationship.multi_datasource.service;

import jpa.practice.relationship.multi_datasource.entity.primary.Author;
import jpa.practice.relationship.multi_datasource.entity.secondary.Book;
import jpa.practice.relationship.multi_datasource.repository.primary.AuthorRepository;
import jpa.practice.relationship.multi_datasource.repository.secondary.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
public class BookStoreService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final @Qualifier("dataSourcePrimaryDb") DataSource dataSourcePrimary;
    private final @Qualifier("dataSourceSecondaryDb") DataSource dataSourceSecondary;

    public BookStoreService(AuthorRepository authorRepository, BookRepository bookRepository, DataSource dataSourcePrimary, DataSource dataSourceSecondary) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;

        this.dataSourcePrimary = dataSourcePrimary;
        this.dataSourceSecondary = dataSourceSecondary;
    }

    @Transactional
    public Author persistAuthor() {
        Author author = Author.builder()
                .name("Alicia Tom")
                .genre("Anthology")
                .age(32)
                .books("Anthology Of 1970")
                .build();
        return authorRepository.save(author);
    }

    @Transactional
    public Book persistBook() {
        Book book = Book.builder()
                .isbn("001-AT")
                .title("Antholoy Of 1970")
                .authors("Alicia Tom")
                .build();

        return bookRepository.save(book);
    }

    //read
    @Transactional(readOnly = true)
    public void readAuthor() {
        authorRepository.findAll().forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void readBook() {
        bookRepository.findAll().forEach(System.out::println);
    }

}
