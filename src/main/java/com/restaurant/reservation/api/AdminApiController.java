package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.MemberApiDto;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.dto.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final MemberRepository memberRepository;
    @GetMapping("/api/admin/member/manage")
    public Page<MemberApiDto> memberManageList(@PageableDefault(page = 0,sort = "id") Pageable pageable){

        /**
         * api/admin/member/manage?size=10&page=1 처럼 전달해 줄 수 있는 듯
         * */
        System.out.println("pageable.toString() = " + pageable.toString());
        Page<Member> memberPage = memberRepository.findAllByMemberRole(MemberRole.CUSTOMER, pageable);

        Page<MemberApiDto> apiDtoPage = memberPage.map(o -> MemberApiDto.createDto(o));


        return apiDtoPage;
    }

    @GetMapping("/api/admin/member/search")
    public Page<MemberApiDto> memberSearchList(MemberSearchCondition condition , @PageableDefault(page = 0,size = 5) Pageable pageable){
        log.info("condition : {}",condition.getSearchType());
        log.info("condition : {}",condition.getKeyword());

//        for (MemberGrade grade : condition.getGrades()) {
//            log.info("grade : {}",grade);
//            log.info("type : {}",grade.getClass());
//        }
        Page<Member> result = memberRepository.findMemberSearchCondition(condition, pageable);
        for (Member member : result) {
            System.out.println("member = " + member);
        }
        Page<MemberApiDto> apiDtoPage = result.map(o -> MemberApiDto.createDto(o));

        return apiDtoPage;
    }

}
