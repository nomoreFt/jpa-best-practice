package jpa.practice.relationship.hibernate_envers.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Entity
/**
 * @Audited: 하이버네이트 엔버스가 이 엔티티를 감시하도록 지정
 * @AuditTable: 엔버스가 생성하는 감사 테이블의 이름을 지정
 */
@Audited
@AuditTable("author_audit")
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;
    private String genre;
    private int age;

    @OneToMany(cascade = CascadeType.ALL,
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
    public String toString() {
        return "Author{" + "id=" + id + ", name=" + name + ", genre=" + genre + ", age=" + age + '}';
    }

    public void setAge(int i) {
        this.age = i;
    }
}