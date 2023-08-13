package com.restaurant.reservation.basic;

import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.web.webDto.MemberWebDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void PagingTest() throws Exception{

        // given
        PageRequest pageRequest = PageRequest.of(0, 3);
        // when
        Page<Member> page = memberRepository.findAllByMemberRole(MemberRole.CUSTOMER, pageRequest);

        List<Member> content = page.getContent();
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(31);  //전체 데이터 수
        assertThat(page.getTotalPages()).isEqualTo(11);  //전체 페이지 번호
        assertThat(page.getNumber()).isEqualTo(0);  //페이지 번호
        assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
        assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?

        Page<MemberWebDto> dtoPage = page.map(o-> MemberWebDto.createDto(o));
        for (MemberWebDto webDto : dtoPage) {
            System.out.println("webDto = " + webDto);
        }

        // then
    }
}
