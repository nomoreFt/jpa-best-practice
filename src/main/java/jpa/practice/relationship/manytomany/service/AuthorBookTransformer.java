package jpa.practice.relationship.manytomany.service;

import jakarta.persistence.Tuple;
import jpa.practice.relationship.manytomany.dto.v1.AuthorBookDtoV1;
import jpa.practice.relationship.manytomany.dto.v1.AuthorWithBooksDtoV1;
import jpa.practice.relationship.manytomany.dto.v1.BookDtoV1;
import jpa.practice.relationship.manytomany.dto.v2.AuthorBookDtoV2;
import jpa.practice.relationship.manytomany.dto.v2.AuthorDtoV2;
import jpa.practice.relationship.manytomany.dto.v2.AuthorWithBooksDtoV2;
import jpa.practice.relationship.manytomany.dto.v2.BookDtoV2;
import jpa.practice.relationship.manytomany.dto.v3.AuthorWithBooksDtoV3;
import jpa.practice.relationship.manytomany.dto.v3.BookDtoV3;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthorBookTransformer {

    public List<AuthorWithBooksDtoV1> transformV1(List<AuthorBookDtoV1> rs) {
        final Map<Long, AuthorWithBooksDtoV1> authorsDtoMap = new HashMap<>();

        for (AuthorBookDtoV1 authorBook : rs) {




            // putIfAbsent를 사용하여 authorWithBooks를 추가
            authorsDtoMap.putIfAbsent(authorBook.authorId(), new AuthorWithBooksDtoV1(
                    authorBook.authorId(),
                    authorBook.authorName(),
                    authorBook.authorGenre(),
                    authorBook.authorAge(),
                    new ArrayList<>()
            ));

            BookDtoV1 book = new BookDtoV1(
                    authorBook.bookId(),
                    authorBook.bookTitle()
            );
            // Map에서 authorWithBooks를 가져와서 책을 추가
            authorsDtoMap.get(authorBook.authorId()).book().add(book);
        }

        return new ArrayList<>(authorsDtoMap.values());
    }

    public List<AuthorWithBooksDtoV2> transformV2(List<AuthorBookDtoV2> rs) {
        final Map<Long, AuthorWithBooksDtoV2> authorsDtoMap = new HashMap<>();

        for (AuthorBookDtoV2 authorBook : rs) {
            AuthorDtoV2 author = authorBook.getAuthor();
            BookDtoV2 book = authorBook.getBook();

            // putIfAbsent를 사용하여 authorWithBooks를 추가
            authorsDtoMap.putIfAbsent(author.getId(), new AuthorWithBooksDtoV2(
                    author.getId(),
                    author.getName(),
                    author.getGenre(),
                    author.getAge(),
                    new ArrayList<>()
            ));

            // Map에서 authorWithBooks를 가져와서 책을 추가
            authorsDtoMap.get(author.getId()).book().add(book);
        }

        return new ArrayList<>(authorsDtoMap.values());
    }

    public List<AuthorWithBooksDtoV3> transformV3(List<Tuple> rs) {
        final Map<Long, AuthorWithBooksDtoV3> authorsDtoMap = new HashMap<>();

        for (Tuple tuple : rs) {
            Long authorId = tuple.get("authorId", Long.class);
            String authorName = tuple.get("authorName", String.class);
            String authorGenre = tuple.get("authorGenre", String.class);
            int authorAge = tuple.get("authorAge", Integer.class);
            Long bookId = tuple.get("bookId", Long.class);
            String bookTitle = tuple.get("bookTitle", String.class);
            String bookIsbn = tuple.get("bookIsbn", String.class);



            // putIfAbsent를 사용하여 authorWithBooks를 추가
            authorsDtoMap.putIfAbsent(authorId, new AuthorWithBooksDtoV3(
                    authorId,
                    authorName,
                    authorGenre,
                    authorAge,
                    new ArrayList<>()
            ));

            BookDtoV3 book = new BookDtoV3(
                    bookId,
                    bookTitle,
                    bookIsbn
            );

            // Map에서 authorWithBooks를 가져와서 책을 추가
            authorsDtoMap.get(authorId).books().add(book);
        }

        return new ArrayList<>(authorsDtoMap.values());
    }
}

