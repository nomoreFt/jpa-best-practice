package jpa.practice.relationship.elementalcollection.repository;

import jpa.practice.relationship.elementalcollection.dto.UserDto;
import jpa.practice.relationship.elementalcollection.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""

            SELECT new jpa.practice.relationship.elementalcollection.dto.UserDto(
                u.id,
                u.name,
                fb
            )
            FROM User u
            JOIN u.favoriteBooks fb
            WHERE u.name = :name
            """
    )
    List<UserDto> findUserDtoByName(String name);
}
