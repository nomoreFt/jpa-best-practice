package jpa.practice.relationship.manytomany.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class AuthorBook implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AuthorBookId id;

    @MapsId("authorId")
    @ManyToOne(fetch = LAZY)
    private Author author;

    @MapsId("bookId")
    @ManyToOne(fetch = LAZY)
    private Book book;

    private Date publishedOn = new Date();

    protected AuthorBook() {}

    public AuthorBook(Author author, Book book) {
        this.author = author;
        this.book = book;
        this.id = AuthorBookId.of(author.getId(), book.getId());
    }

    public AuthorBookId getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setId(AuthorBookId id) {
        this.id = id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final AuthorBook other = (AuthorBook) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }

        if (!Objects.equals(this.book, other.book)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "AuthorBook{" +
                "id=" + id +
                ", publishedOn=" + publishedOn +
                '}';
    }
}
