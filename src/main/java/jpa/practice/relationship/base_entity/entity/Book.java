package jpa.practice.relationship.base_entity.entity;

import jakarta.persistence.*;
import jpa.practice.relationship.base_entity.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Book extends DomainEntity<Book, Long> {
    private String title;
    private String isbn;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    protected Book() {
    }

    @Builder
    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    //toString
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public void setIsbn(String notAvailable) {
        this.isbn = notAvailable;
    }
}
