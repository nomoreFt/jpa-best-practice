package jpa.practice.relationship.onetomany2.repository;

import jpa.practice.relationship.onetomany2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.comments AND
            JOIN FETCH p.images
            WHERE p.title = ?1
            """)
    Post fetchByTitleWithCommentsAndImagesThrowException(String title);


    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.comments
            WHERE p.title = ?1
            """)
    Post fetchByTitleWithComments(String jpa);

    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.images
            WHERE p.title = ?1
            """)
    Post fetchByTitleWithImages(String title);

    @Query("""
            SELECT p
            FROM Post p
            JOIN FETCH p.likes
            WHERE p.title = ?1
            """)
    Post fetchByTitleWithLikes(String jpa);

    @Query("""
            SELECT p
            FROM Post p
            LEFT JOIN FETCH p.comments
            """)
    List<Post> fetchAllWithComments();

    @Query("""
            SELECT p
            FROM Post p
            LEFT JOIN FETCH p.images
            """)
    List<Post> fetchAllWithImages();


    @Query("""
            SELECT p
            FROM Post p
            LEFT JOIN FETCH p.likes
            """)
    List<Post> fetchAllWithLikes();
}
