package jpa.practice.relationship.manytomany.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Author implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String genre;
    private int age;

    @OneToMany(mappedBy = "author"
            , cascade = {CascadeType.PERSIST, CascadeType.MERGE}
            , fetch = FetchType.LAZY)
    private List<AuthorBook> books;

    protected Author() {}

    public Author(String name, String genre, int age) {
        this.name = name;
        this.genre = genre;
        this.age = age;
    }

    public List<AuthorBook> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorBook> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if(!(obj instanceof Author other)) return false;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                ", books=" + books +
                '}';
    }
}

