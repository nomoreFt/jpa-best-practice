package jpa.practice.relationship.onetomany.dto.v1;

public record AuthorWithBookProjectionV1(
        Long authorId,
        String authorName,
        String authorGenre,
        int authorAge,
        Long bookId,
        String bookTitle,
        String bookIsbn
) { }
