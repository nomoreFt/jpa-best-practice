package jpa.practice.relationship.onetomany.dto.v1;

import java.util.List;

public record AuthorWithBooksDtoV1(AuthorDtoV1 author, List<BookDtoV1> books) {
}
