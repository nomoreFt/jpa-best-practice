package jpa.practice.relationship.slow_query_analyzer.service;

import jpa.practice.relationship.slow_query_analyzer.entity.Author;
import jpa.practice.relationship.slow_query_analyzer.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookstoreService {
    private final AuthorRepository authorRepository;

    public BookstoreService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void init() {
        Author author = Author.builder()
                .name("Joana Nimar")
                .genre("Fantasy")
                .age(34)
                .build();

        authorRepository.saveAndFlush(author);
    }

    @Transactional
    public void slowQuery() {
        authorRepository.largeQuery();
    }


}
