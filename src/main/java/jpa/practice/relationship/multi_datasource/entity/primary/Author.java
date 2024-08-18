package jpa.practice.relationship.multi_datasource.entity.primary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import static jakarta.persistence.GenerationType.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String genre;
    private int age;
    private String books;

    protected Author() {
    }

    @Builder
    public Author(String name, String genre, int age, String books) {
        this.name = name;
        this.genre = genre;
        this.age = age;
        this.books = books;
    }

    //getter and setter methods

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                ", books='" + books + '\'' +
                '}';
    }
}
