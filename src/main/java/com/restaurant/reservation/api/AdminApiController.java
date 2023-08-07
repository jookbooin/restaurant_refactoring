package com.restaurant.reservation.api;

import com.restaurant.reservation.api.dto.MemberApiDto;
import com.restaurant.reservation.domain.enumType.MemberType;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminApiController {


    private final MemberRepository memberRepository;
    @GetMapping("/api/admin/member/manage")
    public Page<MemberApiDto> memberManageList(@PageableDefault(size = 7 ,page = 0,sort = "id") Pageable pageable){

        /**
         * api/admin/member/manage?size=10&page=1 처럼 전달해 줄 수 있는 듯
         * */
        System.out.println("pageable.toString() = " + pageable.toString());
        Page<Member> memberPage = memberRepository.findAllByMemberType(MemberType.CUSTOMER, pageable);

        Page<MemberApiDto> apiDtoPage = memberPage.map(o -> MemberApiDto.createDto(o));

        return apiDtoPage;
    }
}
