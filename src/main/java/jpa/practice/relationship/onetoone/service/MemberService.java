package jpa.practice.relationship.onetoone.service;

import jpa.practice.relationship.onetoone.dto.MemberWithDetailsDtoV1;
import jpa.practice.relationship.onetoone.entity.Member;
import jpa.practice.relationship.onetoone.entity.MemberDetails;
import jpa.practice.relationship.onetoone.repository.MemberDetailsRepository;
import jpa.practice.relationship.onetoone.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberDetailsRepository memberDetailsRepository;

    public MemberService(MemberRepository memberRepository, MemberDetailsRepository memberDetailsRepository) {
        this.memberRepository = memberRepository;
        this.memberDetailsRepository = memberDetailsRepository;
    }

    @Transactional
    public void init() {
        Member member1 = new Member("member1");
        MemberDetails memberDetails1 = new MemberDetails("member1's details");
        memberDetails1.setMember(member1);

        Member member2 = new Member("member2");
        MemberDetails memberDetails2 = new MemberDetails("member2's details");
        memberDetails2.setMember(member2);

        memberDetailsRepository.saveAllAndFlush(List.of(memberDetails1, memberDetails2));
    }

    @Transactional(readOnly = true)
    public void fetchMemberWithMemberDetailsById() {
        MemberDetails member = memberDetailsRepository.findByMemberId(1L);
        System.out.println("member = " + member);
        /**
         * [Hibernate]
         *     select
         *         md1_0.member_id,
         *         md1_0.address,
         *         m1_0.id,
         *         m1_0.name
         *     from
         *         member_details md1_0
         *     join
         *         member m1_0
         *             on m1_0.id=md1_0.member_id
         *     where
         *         m1_0.id=?
         */
    }

    @Transactional(readOnly = true)
    public void fetchMemberWithMemberDetailsByIdViaDtoV1() {
        MemberWithDetailsDtoV1 member = memberDetailsRepository.fetchByMemberIdViaDtoV1(1L);
        System.out.println("member = " + member);
        /**
         * [Hibernate]
         *     select
         *         md1_0.member_id,
         *         m1_0.name,
         *         md1_0.address
         *     from
         *         member_details md1_0
         *     join
         *         member m1_0
         *             on m1_0.id=md1_0.member_id
         *     where
         *         m1_0.id=?
         */
    }
    @Transactional(readOnly = true)
    public void fetchAllViaDtosV1() {
        List<MemberWithDetailsDtoV1> all = memberDetailsRepository.fetchAllViaDtosV1();
        System.out.println("all = " + all);
        /**
         * [Hibernate]
         *     select
         *         md1_0.member_id,
         *         m1_0.name,
         *         md1_0.address
         *     from
         *         member_details md1_0
         *     join
         *         member m1_0
         *             on m1_0.id=md1_0.member_id
         *
         * all = [MemberWithDetailsDtoV1[id=1, name=member1, address=member1's details], MemberWithDetailsDtoV1[id=2, name=member2, address=member2's details]]
         */
    }
}
