package jpa.practice.relationship.base_entity.repository;

import jpa.practice.relationship.base_entity.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name); //select * from author where name =
}
