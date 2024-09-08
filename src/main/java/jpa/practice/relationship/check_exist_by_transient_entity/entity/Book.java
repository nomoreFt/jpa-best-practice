package jpa.practice.relationship.check_exist_by_transient_entity.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Getter
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private String isbn;
    private int price;

    @ManyToOne
    private Author author;

    protected Book() {
    }

    @Builder
    public Book(String title, String genre, String isbn, int price) {
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.price = price;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}