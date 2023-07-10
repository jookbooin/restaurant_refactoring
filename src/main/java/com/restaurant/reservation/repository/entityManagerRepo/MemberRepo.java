package com.restaurant.reservation.repository.entityManagerRepo;

import com.restaurant.reservation.domain.members.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepo {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member); // persist 한다고 insert 문이 나가는 것이 아님
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


}
