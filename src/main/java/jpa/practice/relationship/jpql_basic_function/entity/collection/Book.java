package jpa.practice.relationship.jpql_basic_function.entity.collection;

import jakarta.persistence.Embeddable;
import lombok.experimental.SuperBuilder;

@Embeddable
public class Book {
    private String title;
    private String author;

    // 기본 생성자
    public Book() {}

    // 생성자
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}