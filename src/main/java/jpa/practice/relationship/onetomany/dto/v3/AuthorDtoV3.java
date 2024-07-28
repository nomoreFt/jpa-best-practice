package jpa.practice.relationship.onetomany.dto.v3;

public record AuthorDtoV3(
        Long id,
        String name,
        String genre,
        Integer age
) {
}
