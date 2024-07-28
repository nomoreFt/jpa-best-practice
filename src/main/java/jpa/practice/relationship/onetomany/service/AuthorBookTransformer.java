package jpa.practice.relationship.onetomany.service;

import jakarta.persistence.Tuple;
import jpa.practice.relationship.onetomany.dto.v1.AuthorWithBookProjectionV1;
import jpa.practice.relationship.onetomany.dto.v1.AuthorWithBooksDtoV1;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBookProjectionV2;
import jpa.practice.relationship.onetomany.dto.v2.AuthorWithBooksDtoV2;
import jpa.practice.relationship.onetomany.dto.v3.AuthorWithBooksDtoV3;

import java.util.List;

public interface AuthorBookTransformer {
    AuthorWithBooksDtoV1 toAuthorWithBooksDtoV1(List<AuthorWithBookProjectionV1> projections);

    AuthorWithBooksDtoV2 toAuthorWithBooksDtoV2(List<AuthorWithBookProjectionV2> authorWithBookDtoV2);

    List<AuthorWithBooksDtoV3> toAuthorWithBooksDtoV3(List<Tuple> authorWithBooksTuple);
}
