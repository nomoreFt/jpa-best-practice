package jpa.practice.relationship.multi_datasource.repository.primary;

import jpa.practice.relationship.multi_datasource.entity.primary.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
