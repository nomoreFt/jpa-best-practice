package jpa.practice.relationship.manytomany.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuthorBookId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "book_id")
    private Long bookId;

    protected AuthorBookId() {
    }

    private AuthorBookId(Long authorId, Long bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public static AuthorBookId of(Long authorId, Long bookId) {
        return new AuthorBookId(authorId, bookId);
    }


    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.authorId);
        hash = 31 * hash + Objects.hashCode(this.bookId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof AuthorBookId other)) return false;

        if (!Objects.equals(this.authorId, other.authorId)) return false;
        if (!Objects.equals(this.bookId, other.bookId)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "AuthorBookId{" +
                "authorId=" + authorId +
                ", bookId=" + bookId +
                '}';
    }
}
