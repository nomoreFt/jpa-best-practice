package jpa.practice.relationship.hibernate_envers.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serializable;

@Getter
@Entity
/**
 * @Audited: 하이버네이트 엔버스가 이 엔티티를 감시하도록 지정
 * @AuditTable: 엔버스가 생성하는 감사 테이블의 이름을 지정
 */
@Audited
@AuditTable("book_audit")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String title;
    private String isbn;

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

        return id != null && id.equals(((Book) obj).id);
    }

    @Override
    public int hashCode() {
        return 2021;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", isbn=" + isbn + '}';
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setIsbn(String notAvailable) {
        this.isbn = notAvailable;
    }
}