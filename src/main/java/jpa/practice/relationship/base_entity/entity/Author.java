package jpa.practice.relationship.base_entity.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jpa.practice.relationship.base_entity.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Entity
public class Author extends DomainEntity<Author, Long> {
    private String name;
    private String genre;
    private int age;

    @Version
    private Long version;

    @OneToMany(cascade = {PERSIST, MERGE},
            mappedBy = "author", orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    protected Author() {
    }

    @Builder
    public Author(String name, String genre, int age) {
        this.name = name;
        this.genre = genre;
        this.age = age;
    }

    //helper methods
    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        book.setAuthor(null);
        this.books.remove(book);
    }

    public void removeBooks() {
        Iterator<Book> iterator = this.books.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();

            book.setAuthor(null);
            iterator.remove();
        }
    }


    @Override
    public Long getId() {
        return id;
    }

    //toString
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                '}';
    }

    public void old(int i) {
        this.age = i;
    }
}
