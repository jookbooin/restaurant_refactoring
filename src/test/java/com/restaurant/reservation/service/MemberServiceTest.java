package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Dto.MemberDto;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {
@Autowired
MemberService memberService;
@Autowired
MemberRepository memberRepository;
    @Test
    public void loginTest() throws Exception{

        // given

        // when

        // then
    }

    @Test
    @Rollback(false)
    public void updateTest() throws Exception{

        // given
        MemberDto memberDto1=MemberDto.builder()
                .id(1L)
                .email("3670lsh")
                .password("1111")
                .name("lsh")
                .phoneNumber("01010")
                .build();

        Member findMember= memberRepository.findById(1L).get();
        System.out.println("findMember = " + findMember);

        memberService.updateMember(memberDto1);
    }


}