package jpa.practice.relationship.sqlcount_assert.service;

import jpa.practice.relationship.sqlcount_assert.entity.Author;
import jpa.practice.relationship.sqlcount_assert.repository.AuthorRepository;
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

    public void updateAuthor() {

        Author author = authorRepository.findById(1L).orElseThrow();
        author.setGenre("History");

        authorRepository.save(author);
    }


}
