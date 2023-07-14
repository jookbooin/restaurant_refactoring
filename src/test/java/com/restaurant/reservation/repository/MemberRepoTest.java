package com.restaurant.reservation.repository;

import com.restaurant.reservation.repository.entityManagerRepo.MemberRepo;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.dto.MemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepoTest {

    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private MemberRepository memberRepository;
    @Test
    @Rollback(false)
    public void memberTest() throws Exception{
        MemberDto memberDto1=MemberDto.builder()
                .email("3670lsh")
                .password("1111")
                .name("lsh")
                .phoneNumber("01010")
                .build();
        Member member1= Member.createCustomer(memberDto1);
        Member insertMember1 = memberRepository.save(member1);
        Assertions.assertEquals(insertMember1.getId(),1);

        MemberDto memberDto2=MemberDto.builder()
                .email("lsh3661")
                .password("2222")
                .name("이승헌")
                .phoneNumber("7197")
                .build();
        Member member2= Member.createCustomer(memberDto2);
        Member insertMember2 = memberRepository.save(member2);
        Assertions.assertEquals(insertMember2.getId(),2);


        Member findMember = memberRepository.findById(member1.getId()).get();

    }

    @Test
//    @Rollback(false)
    public void memberFindTest() throws Exception{

    }



}