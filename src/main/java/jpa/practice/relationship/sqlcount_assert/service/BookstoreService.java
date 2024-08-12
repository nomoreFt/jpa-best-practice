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

    /**
     * 개발자가 실수로 @Transactional을 붙이지 않고 엔티티를 수정하는 나쁜 방법
     * 개별 쿼리가 나가 1SELECT, 1UPDATE가 아닌 2SELECT, 1UPDATE가 발생한다.
     * UPDATE 전에 SELECT 한번 더함
     *
     * @return
     */
    public Long updateAuthor_Bad(Long authorId, String genre, int age) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        author.setGenre(genre);
        author.setAge(age);
        return authorRepository.save(author).getId();
    }

    /**
     * 엔티티를 수정하는 좋은 방법
     * @Transactional을 붙이고 더티체킹을 이용하여 엔티티를 수정한다.
     * 1SELECT, 1UPDATE가 예상대로 발생해야 한다.
     */
    @Transactional
    public Long updateAuthor_Good(Long authorId, String genre, int age) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        author.setGenre(genre);
        author.setAge(age);
        return author.getId();
    }


}
