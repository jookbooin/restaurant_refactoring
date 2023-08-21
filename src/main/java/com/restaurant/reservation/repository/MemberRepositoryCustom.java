package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Member> findMemberSearchCondition(MemberSearch condition, Pageable pageable);
}
