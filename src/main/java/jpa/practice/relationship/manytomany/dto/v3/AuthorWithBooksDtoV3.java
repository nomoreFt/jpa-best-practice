package jpa.practice.relationship.manytomany.dto.v3;

import java.util.List;

public record AuthorWithBooksDtoV3(Long id, String name, String genre, int age, List<BookDtoV3> books) {
}
