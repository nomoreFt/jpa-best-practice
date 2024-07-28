package jpa.practice.relationship.onetomany.repository;

import jakarta.persistence.Tuple;
import jpa.practice.relationship.manytomany.dto.v1.AuthorBookDtoV1;
import jpa.practice.relationship.onetomany.dto.v1.AuthorDtoV1;
import jpa.practice.relationship.onetomany.dto.v1.AuthorWithBookProjectionV1;
import jpa.practice.relationship.onetomany.dto.v1.AuthorWithBooksDtoV1;
import jpa.practice.relationship.onetomany.dto.v1.BookDtoV1;
import jpa.practice.relationship.onetomany.dto.v2.AuthorDtoV2;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBookProjectionV2;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBooksDtoV2;
import jpa.practice.relationship.onetomany.dto.v2.BookDtoV2;
import jpa.practice.relationship.onetomany.dto.v3.AuthorDtoV3;
import jpa.practice.relationship.onetomany.dto.v3.AuthorWithBooksDtoV3;
import jpa.practice.relationship.onetomany.dto.v3.BookDtoV3;
import jpa.practice.relationship.onetomany.service.AuthorBookTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorBookTransformerImpl implements AuthorBookTransformer {

    @Override
    public AuthorWithBooksDtoV1 toAuthorWithBooksDtoV1(List<AuthorWithBookProjectionV1> rs) {

        final Map<Long, AuthorWithBooksDtoV1> authorsDtoMap = new HashMap<>();

        for (AuthorWithBookProjectionV1 dto : rs) {
            authorsDtoMap.putIfAbsent(dto.authorId()
                    , new AuthorWithBooksDtoV1(
                            new AuthorDtoV1(dto.authorId()
                                    , dto.authorName()
                                    , dto.authorGenre()
                                    , dto.authorAge()
                            )
                            , new ArrayList<>()
                    )
            );

            BookDtoV1 book = new BookDtoV1(dto.bookId(), dto.bookTitle());
            authorsDtoMap.get(dto.authorId()).books().add(book);
        }

        return authorsDtoMap.values().stream().findFirst().orElse(null);
    }

    @Override
    public AuthorWithBooksDtoV2 toAuthorWithBooksDtoV2(List<AuthorWithBookProjectionV2> authorWithBookDtoV2) {

            final Map<Long, AuthorWithBooksDtoV2> authorsDtoMap = new HashMap<>();

            for (AuthorWithBookProjectionV2 dto : authorWithBookDtoV2) {
                AuthorDtoV2 author = dto.getAuthor();
                BookDtoV2 book = dto.getBook();

                authorsDtoMap.putIfAbsent(author.getId()
                        , new AuthorWithBooksDtoV2(
                                author
                                , new ArrayList<>()
                        )
                );

                authorsDtoMap.get(author.getId()).books().add(book);
            }

            return authorsDtoMap.values().stream().findFirst().orElse(null);
    }

    @Override
    public List<AuthorWithBooksDtoV3> toAuthorWithBooksDtoV3(List<Tuple> authorWithBooksTuple) {
        final Map<Long, AuthorWithBooksDtoV3> authorsDtoMap = new HashMap<>();

        for (Tuple tuple : authorWithBooksTuple) {
            Long authorId = tuple.get("authorId", Long.class);
            String authorName = tuple.get("authorName", String.class);
            String authorGenre = tuple.get("authorGenre", String.class);
            Integer authorAge = tuple.get("authorAge", Integer.class);
            Long bookId = tuple.get("bookId", Long.class);
            String bookTitle = tuple.get("bookTitle", String.class);
            String bookIsbn = tuple.get("bookIsbn", String.class);

            authorsDtoMap.putIfAbsent(authorId
                    , new AuthorWithBooksDtoV3(
                            new AuthorDtoV3(authorId, authorName, authorGenre, authorAge)
                            , new ArrayList<>()
                    )
            );

            authorsDtoMap.get(authorId).books().add(new BookDtoV3(bookId, bookTitle, bookIsbn));
        }

        return new ArrayList<>(authorsDtoMap.values());
    }
}
