package jpa.practice.relationship.check_exist_by_transient_entity.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String name;
    private String genre;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();

    protected Author() {
    }

    @Builder
    public Author(int age, String name, String genre) {
        this.age = age;
        this.name = name;
        this.genre = genre;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", age=" + age + ", name=" + name + ", genre=" + genre + '}';
    }
}
