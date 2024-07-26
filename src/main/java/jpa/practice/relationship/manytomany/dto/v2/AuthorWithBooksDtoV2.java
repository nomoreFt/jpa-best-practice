package jpa.practice.relationship.manytomany.dto.v2;

import java.util.List;

public record AuthorWithBooksDtoV2(Long id, String name, String genre, String age, List<BookDtoV2> book) {
}
