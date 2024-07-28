package jpa.practice.relationship.manytomany.service;

import jakarta.persistence.Tuple;
import jpa.practice.relationship.manytomany.dto.v1.AuthorBookDtoV1;
import jpa.practice.relationship.manytomany.dto.v1.AuthorWithBooksDtoV1;
import jpa.practice.relationship.manytomany.dto.v2.AuthorBookDtoV2;
import jpa.practice.relationship.manytomany.dto.v2.AuthorWithBooksDtoV2;
import jpa.practice.relationship.manytomany.dto.v3.AuthorWithBooksDtoV3;

import java.util.List;

public interface AuthorBookTransformer {
    List<AuthorWithBooksDtoV1> transformV1(List<AuthorBookDtoV1> rs);

    List<AuthorWithBooksDtoV2> transformV2(List<AuthorBookDtoV2> rs);

    List<AuthorWithBooksDtoV3> transformV3(List<Tuple> rs);
}
