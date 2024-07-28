package jpa.practice.relationship.onetomany2.repository;

import jpa.practice.relationship.onetomany2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
