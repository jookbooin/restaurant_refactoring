package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.enumType.MemberGrade;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.MemberSearch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
class MemberRepositoryImplTest {
    @Autowired
    MemberRepository memberRepository;


//    @BeforeEach
//    public void before(){
//
//        List<String> emailList = Arrays.asList("@naver.com","@gmail.com","@empal.com");
//
//        List<String> nameList = new ArrayList<>();
//        nameList.add("고객");
//        nameList.add("zzzz");
//        nameList.add("ttttt");
//        nameList.add("bbb");
//
//
//        for (int i = 3; i < 103; i++) {
//            MemberDto memberDto = MemberDto.builder()
//                    .email("3670lsh@naver.c"+i)
//                    .password("dltmdgjs413!"+i)
//                    .name("고객"+i)
//                    .phoneNumber("010719741"+i)
//                    .build();
//
//            int divInt = i % 4 ;
//            MemberInfo info = new MemberInfo(nameList.get(divInt), memberDto.getPhoneNumber());
//
//            Member member = null;
//
//            if (i%3==1){
//                member = new Member(emailList.get(1), memberDto.getPassword(), info , MemberGrade.BRONZE, MemberRole.CUSTOMER);
//            }
//            else if (i%3==2){
//                member = new Member(emailList.get(2), memberDto.getPassword(), info , MemberGrade.SILVER, MemberRole.CUSTOMER);
//            }
//            else
//                member =  new Member(emailList.get(0), memberDto.getPassword(), info , MemberGrade.GOLD, MemberRole.CUSTOMER);
//
//            memberRepository.save(member);
//        }
//    }


    @Test
    public void 테스트() throws Exception{

        List<Member> result = memberRepository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(100);
    }

    @Test
    public void QueryDsl_Dynamic() throws Exception{

        PageRequest pageRequest = PageRequest.of(0,5);

        MemberSearch msc = new MemberSearch();

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