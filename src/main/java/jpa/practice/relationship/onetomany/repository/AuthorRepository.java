package jpa.practice.relationship.onetomany.repository;

import jakarta.persistence.Tuple;
import jpa.practice.relationship.onetomany.dto.v1.AuthorWithBookProjectionV1;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBookProjectionV2;
import jpa.practice.relationship.onetomany.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("""
            SELECT a
            FROM Author a
            JOIN FETCH a.books
            WHERE a.id = :id
            """)
    Author fetchWithBooksById(Long id);

    @Query("""
            SELECT new jpa.practice.relationship.onetomany.dto.v1.AuthorWithBookProjectionV1(
                a.id,
                a.name,
                a.genre,
                a.age,
                b.id,
                b.title,
                b.isbn
            )
            FROM Author a
            LEFT JOIN Book b ON a.id = b.author.id
            WHERE a.id = :id
            """)
    List<AuthorWithBookProjectionV1> fetchWithBooksByIdDtoV1(Long id);

    @Query("""
            SELECT a as author
            , b as book
            FROM Author a
            LEFT JOIN Book b ON a.id = b.author.id
            WHERE a.id = :id
            """)
    List<AuthorWithBookProjectionV2> fetchWithBooksProjectionByIdV2(Long id);

    @Query("""
            SELECT 
            a.id as authorId
            , a.name as authorName
            , a.genre as authorGenre
            , a.age as authorAge
            , b.id as bookId
            , b.title as bookTitle
            , b.isbn as bookIsbn
            FROM Author a
            LEFT JOIN Book b ON a.id = b.author.id
            WHERE a.id = :id
            """)
    List<Tuple> fetchWithBooksTupleByIdV3(Long id);
}
