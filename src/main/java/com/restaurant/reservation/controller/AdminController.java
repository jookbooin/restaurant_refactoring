package com.restaurant.reservation.controller;

import com.restaurant.reservation.api.dto.MemberSearchApiCondition;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.dto.MemberSearchCondition;
import com.restaurant.reservation.service.MemberService;
import com.restaurant.reservation.web.webDto.MemberWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String memberManageList(Model model, @ModelAttribute("memberSearch") MemberSearchApiCondition condition , @PageableDefault(page = 0,size = 5) Pageable pageable){

        log.info("MemberSearchCondition : {}",condition);
        log.info("pageable : {}",pageable);

        MemberSearchCondition searchCondition = MemberSearchCondition.createSearchCondition(condition);
        Page<Member> memberPage = memberService.findMemberAll(searchCondition, pageable);
        Page<MemberWebDto> dtoPage = memberPage.map(o -> MemberWebDto.createDto(o));

        List<MemberWebDto> dtoList = dtoPage.getContent();

        model.addAttribute("dtoPage",dtoPage);

        return "basic/admin/memberManageList";
    }

//    @PostMapping("/admin/member/list")
}

