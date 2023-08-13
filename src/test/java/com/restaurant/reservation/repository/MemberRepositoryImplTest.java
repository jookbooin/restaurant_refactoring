package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.members.MemberInfo;
import com.restaurant.reservation.repository.dto.MemberSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MemberRepositoryImplTest {
    @Autowired
    MemberRepository memberRepository;


    @BeforeEach
    public void before(){
        List<String> emailList = new ArrayList<>();
        emailList.add("naver");
        emailList.add("gmail");
        emailList.add("kkao");

        List<String> nameList = new ArrayList<>();
        nameList.add("cccc");
        nameList.add("zzzz");
        nameList.add("ttttt");
        nameList.add("bbb");


        for (int i = 3; i < 103; i++) {
            MemberDto memberDto = MemberDto.builder()
                    .email("3670lsh@naver.c"+i)
                    .password("dltmdgjs413!"+i)
                    .name("고객"+i)
                    .phoneNumber("010719741"+i)
                    .build();

            int divInt = i % 4 ;
            MemberInfo info = new MemberInfo(nameList.get(divInt), memberDto.getPhoneNumber());

            Member member = null;

            if (i%3==1){
                member = new Member(emailList.get(1), memberDto.getPassword(), info , MemberGrade.BRONZE, MemberRole.CUSTOMER);
            }
            else if (i%3==2){
                member = new Member(emailList.get(2), memberDto.getPassword(), info , MemberGrade.SILVER, MemberRole.CUSTOMER);
            }
            else
                member =  new Member(emailList.get(0), memberDto.getPassword(), info , MemberGrade.GOLD, MemberRole.CUSTOMER);
//
            memberRepository.save(member);
        }
    }

    @Test
    public void QueryDsl_Dynamic() throws Exception{

        PageRequest pageRequest = PageRequest.of(0,5);

        MemberSearchCondition msc = new MemberSearchCondition();

        msc.getGrades().add(MemberGrade.BRONZE);
        msc.getGrades().add(MemberGrade.SILVER);
        msc.setSearchType("email");
        msc.setKeyword("k");

        Page<Member> result = memberRepository.findMemberSearchCondition(msc, pageRequest);
        Assertions.assertThat(result.getSize()).isEqualTo(5);
        Assertions.assertThat(result.getContent().size()).isEqualTo(5);

        List<Member> content = result.getContent();

        for (Member member : content) {
            System.out.println("member = " + member);
        }

    }


}