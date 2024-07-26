package jpa.practice.relationship.manytomany.repository;


import jakarta.persistence.Tuple;
import jpa.practice.relationship.manytomany.dto.v1.AuthorBookDtoV1;
import jpa.practice.relationship.manytomany.dto.v2.AuthorBookDtoV2;
import jpa.practice.relationship.manytomany.entity.AuthorBook;
import jpa.practice.relationship.manytomany.entity.AuthorBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AuthorBookRepository extends JpaRepository<AuthorBook, AuthorBookId> {
    @Query("""
                SELECT ab
                FROM AuthorBook ab
                JOIN FETCH ab.author
                JOIN FETCH ab.book
            """)
    List<AuthorBook> fetchAll();

    @Query("""
            SELECT new jpa.practice.relationship.manytomany.dto.v1.AuthorBookDtoV1(
                a.id,
                a.name,
                a.genre,
                a.age,
                b.id,
                b.title,
                b.isbn
            )
            FROM AuthorBook ab
            JOIN ab.author a
            JOIN ab.book b
            """)
    List<AuthorBookDtoV1> findByViaDTOV1();

    @Query("""
            SELECT a as author,
                   b as book
            FROM AuthorBook ab
            JOIN ab.author a
            JOIN ab.book b
            """)
    List<AuthorBookDtoV2> findByViaDTOV2();

    @Query("""
            SELECT a.id as authorId,
                   a.name as authorName,
                   a.genre as authorGenre,
                   a.age as authorAge,
                   b.id as bookId,
                   b.title as bookTitle,
                   b.isbn as bookIsbn
            FROM AuthorBook ab
            JOIN ab.author a
            JOIN ab.book b
            """)
    List<Tuple> findByViaTupleV3();
}