package jpa.practice.relationship.onetomany.dto.v2;

public interface AuthorWithBookProjectionV2 {
    AuthorDtoV2 getAuthor();
    BookDtoV2 getBook();
}
