package jpa.practice.relationship.slow_query_analyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer age;
    private String genre;
    private String name;

    protected Author() {}

    @Builder
    public Author(Integer age, String genre, String name) {
        this.age = age;
        this.genre = genre;
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(obj instanceof Author other)) return false;
        return id != null && Objects.equals(id, other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", age=" + age +
                ", genre='" + genre + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setAge(int i) {
        this.age = i;
    }
}
