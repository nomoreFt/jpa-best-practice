package jpa.practice.relationship.onetomany2.repository;

import jpa.practice.relationship.onetomany2.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ImageRepository extends JpaRepository<Image, Long> {
}
