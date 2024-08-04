package jpa.practice.relationship.jpql_basic_function.entity.collection;

import lombok.Builder;

public class Novel extends Book {
    private String genre;

    // 기본 생성자
    public Novel() {}

    // 생성자
    @Builder
    public Novel(String title, String author, String genre) {
        super(title, author);
        this.genre = genre;
    }

    // Getters and Setters
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
