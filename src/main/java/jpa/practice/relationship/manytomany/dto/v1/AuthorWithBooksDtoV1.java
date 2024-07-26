package jpa.practice.relationship.manytomany.dto.v1;

import java.util.List;

public record AuthorWithBooksDtoV1(Long id, String name, String genre, int age, List<BookDtoV1> book) {
}
