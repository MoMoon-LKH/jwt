package com.example.jwt.repository;


import com.example.jwt.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }


    public Optional<Member> findOneByEmail(String email) {
        return Optional.ofNullable(em.createQuery("select m from Member m where m.email = :email", Member.class)
        .setParameter("email",email)
        .getSingleResult());
    }



    public List<Member> findMembers(Member member) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", member.getEmail())
                .getResultList();
    }

    public void duplicateMember(Member member) {
        List<Member> memberList = findMembers(member);

        if (!memberList.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

}
