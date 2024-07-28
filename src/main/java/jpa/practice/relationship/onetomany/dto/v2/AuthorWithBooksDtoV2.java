package jpa.practice.relationship.onetomany.dto.v2;

import java.util.List;

public record AuthorWithBooksDtoV2(
        AuthorDtoV2 author,
        List<BookDtoV2> books
) {
}
