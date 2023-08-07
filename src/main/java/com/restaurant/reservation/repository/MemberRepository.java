package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.enumType.MemberType;
import com.restaurant.reservation.domain.members.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

//    List<Member> findAllByMemberType(MemberType memberType);

    Page<Member> findAllByMemberType(MemberType memberType, Pageable pageable);

}
