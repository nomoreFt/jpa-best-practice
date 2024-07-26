package jpa.practice.relationship.manytomany.dto.v1;

public record AuthorBookDtoV1(
        Long authorId,
        String authorName,
        String authorGenre,
        int authorAge,
        Long bookId,
        String bookTitle,
        String bookIsbn
) {
}
