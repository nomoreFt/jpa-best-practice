package jpa.practice.relationship.hibernate_envers.service;

import jakarta.persistence.EntityManager;
import jpa.practice.relationship.hibernate_envers.entity.Author;
import jpa.practice.relationship.hibernate_envers.entity.Book;
import jpa.practice.relationship.hibernate_envers.repository.AuthorRepository;
import jpa.practice.relationship.hibernate_envers.repository.BookRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RevisionService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuditReader auditReader;

    public RevisionService(AuthorRepository authorRepository, BookRepository bookRepository, AuditReader auditReader) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.auditReader = auditReader;
    }


    /**
     * Author의 ID로 변경 이력을 조회한다.
     */
    @Transactional(readOnly = true)
    public void queryEntityHistoryById() {

        Long markJanel = authorRepository.findByName("Mark Janel").getId();
        Revision<Integer, Author> integerAuthorRevision = authorRepository.findLastChangeRevision(markJanel).get();
        Author author = integerAuthorRevision.getEntity();
        System.out.println("Author: " + author);
    }

    /**
     * 특정 Author의 모든 변경 이력을 조회한다.
     */
    public void queryEntityHistoryByAuthor() {
        Revisions<Integer, Author> revisions = authorRepository.findRevisions(2L);
        revisions.forEach(System.out::println);
        /**
         * Revision 1 of entity Author{id=2, name=Mark Janel, genre=Anthology, age=23} - Revision metadata DefaultRevisionMetadata{entity=DefaultRevisionEntity(id = 1, revisionDate = Aug 19, 2024, 1:56:07 AM), revisionType=INSERT}
         * Revision 2 of entity Author{id=2, name=Mark Janel, genre=Anthology, age=45} - Revision metadata DefaultRevisionMetadata{entity=DefaultRevisionEntity(id = 2, revisionDate = Aug 19, 2024, 1:56:07 AM), revisionType=UPDATE}
         * Revision 3 of entity Author{id=2, name=Mark Janel, genre=Anthology, age=47} - Revision metadata DefaultRevisionMetadata{entity=DefaultRevisionEntity(id = 3, revisionDate = Aug 19, 2024, 1:56:07 AM), revisionType=UPDATE}
         */
    }

    /**
     * 가장 최근 변경 이력을 조회한다.(Ver 1. Repository 버전)
     */
    public void queryLatestEntityHistory() {
        authorRepository.findLastChangeRevision(2L).ifPresent((revision) -> {
            Author author = revision.getEntity();
            /**
             * Author: Author{id=2, name=Mark Janel, genre=Anthology, age=47}
             */
            System.out.println("Author: " + author);
        });
    }

    /**
     * 가장 최근 변경 이력을 조회한다.(Ver 2. AuditReader 버전)
     */
    public void queryLatestEntityHistory2() {
        AuditQuery query = auditReader.createQuery()
                .forRevisionsOfEntity(Author.class, false, true)
                .add(AuditEntity.id().eq(2L))
                .addOrder(AuditEntity.revisionNumber().desc())
                .setMaxResults(1);

        /**
         * Author: Author{id=2, name=Mark Janel, genre=Anthology, age=47}
         */
        List<Object[]> authors = query.getResultList();
        authors.forEach((objects) -> {
            Author author = (Author) objects[0];
            System.out.println("Author: " + author);
        });

    }


    //변경 기간 별로 검색


}
