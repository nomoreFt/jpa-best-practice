package jpa.practice.relationship.jpql_basic_function.entity.collection;

import lombok.Builder;

public class TextBook extends Book {
    private String subject;

    // 기본 생성자
    public TextBook() {}

    // 생성자
    @Builder
    public TextBook(String title, String author, String subject) {
        super(title, author);
        this.subject = subject;
    }

    // Getters and Setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "TextBook{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
