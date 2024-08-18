package jpa.practice.relationship.multi_datasource.entity.secondary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import static jakarta.persistence.GenerationType.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String authors;

    protected Book() {
    }

    @Builder
    public Book(String title, String isbn, String authors) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
    }

    //getter and setter methods

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", authors='" + authors + '\'' +
                '}';
    }
}
