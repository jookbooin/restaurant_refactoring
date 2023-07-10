package com.restaurant.reservation;

import com.restaurant.reservation.domain.Dto.MemberDto;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct // bean에 올라오면 spring이 불러오는 것 : 초기화
    public void init() {
        initService.dbInit1();
        initService.dbInit2();

    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        @Autowired
        private final MemberRepository memberRepository;

        public void dbInit1() {
            MemberDto memberDto1 =MemberDto.builder()
                    .email("3670lsh@naver.com")
                    .password("dltmdgjs4139!")
                    .name("고객3670")
                    .phoneNumber("01071974139")
                    .build();
            Member member1= Member.createCustomer(memberDto1);
            Member insertMember1 = memberRepository.save(member1);
        }

        public void dbInit2(){
            MemberDto memberDto2=MemberDto.builder()
                    .email("3670lsh@gmail.com")
                    .password("dltmdgjs4139!")
                    .name("관리자3670")
                    .phoneNumber("01041397197")
                    .build();
            Member member2= Member.createAdmin(memberDto2);
            Member insertMember2 = memberRepository.save(member2);
        }
    }
}
