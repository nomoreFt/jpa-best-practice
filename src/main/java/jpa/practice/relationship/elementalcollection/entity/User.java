package jpa.practice.relationship.elementalcollection.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ElementalCollection은 기본적으로 변경이 자주 되지 않는 값 객체 리스트면 좋다.
 * 변경이 되는 경우 싹 비우고 다시 insert하기 때문.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "user_favorite_books", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "book_title")
    @OrderColumn(name = "book_order")
    private List<String> favoriteBooks = new ArrayList<>();

    protected User() {}

    public User(String name) {
        this.name = name;
    }

    //helper methods
    public void addFavoriteBook(String book) {
        favoriteBooks.add(book);
    }

    public void removeFavoriteBook(String book) {
        favoriteBooks.remove(book);
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", favoriteBooks=" + favoriteBooks +
                '}';
    }
}
