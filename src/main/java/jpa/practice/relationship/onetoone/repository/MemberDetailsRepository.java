package jpa.practice.relationship.onetoone.repository;

import jpa.practice.relationship.onetoone.dto.MemberWithDetailsDtoV1;
import jpa.practice.relationship.onetoone.entity.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MemberDetailsRepository extends JpaRepository<MemberDetails, Long> {
    @Query("""
            SELECT md
            FROM MemberDetails md
            JOIN FETCH md.member
            WHERE md.member.id = :id
            """)
    MemberDetails findByMemberId(Long id);

    @Query("""
                        SELECT new jpa.practice.relationship.onetoone.dto.MemberWithDetailsDtoV1(
                            md.id,
                            md.member.name,
                            md.address
                        )
                        FROM MemberDetails md
                        JOIN md.member
                        WHERE md.member.id = :id
            """)
    MemberWithDetailsDtoV1 fetchByMemberIdViaDtoV1(Long id);

    @Query("""
                        SELECT new jpa.practice.relationship.onetoone.dto.MemberWithDetailsDtoV1(
                            md.id,
                            md.member.name,
                            md.address
                        )
                        FROM MemberDetails md
                        JOIN md.member
            """)
    List<MemberWithDetailsDtoV1> fetchAllViaDtosV1();
}
