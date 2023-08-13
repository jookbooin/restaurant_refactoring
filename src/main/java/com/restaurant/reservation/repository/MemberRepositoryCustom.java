package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Member> findMemberSearchCondition(MemberSearchCondition condition, Pageable pageable);
}
