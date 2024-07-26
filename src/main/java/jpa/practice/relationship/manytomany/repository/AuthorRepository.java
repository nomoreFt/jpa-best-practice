package jpa.practice.relationship.manytomany.repository;


import jpa.practice.relationship.manytomany.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}