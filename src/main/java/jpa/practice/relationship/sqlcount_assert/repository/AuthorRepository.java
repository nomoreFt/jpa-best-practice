package jpa.practice.relationship.sqlcount_assert.repository;

import jpa.practice.relationship.sqlcount_assert.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long>{
}
