package com.restaurant.reservation.controller;

import com.restaurant.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;


    @GetMapping("/admin/member/manage")
    public String memberManageList(@PageableDefault(size = 7 , page= 2,sort = "id")Pageable pageable, Model model){

//        Page<Member> memberPage = memberRepository.findAllByMemberType(MemberType.CUSTOMER, pageable);
//
//        List<MemberWebDto> webDtoList = memberPage.map(o -> MemberWebDto.createDto(o)).getContent();
////        Page<MemberWebDto> webDtoPage = memberPage.map(o -> MemberWebDto.createDto(o));
//
//        model.addAttribute("memberList",webDtoList);

        return "basic/admin/memberManageList";
    }
}
