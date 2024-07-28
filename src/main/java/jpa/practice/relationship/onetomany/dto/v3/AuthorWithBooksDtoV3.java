package jpa.practice.relationship.onetomany.dto.v3;

import java.util.List;

public record AuthorWithBooksDtoV3(
        AuthorDtoV3 author,
        List<BookDtoV3> books
) {}
