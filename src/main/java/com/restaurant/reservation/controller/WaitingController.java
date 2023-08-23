package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.dto.MemberDto;
import com.restaurant.reservation.service.MemberService;
import com.restaurant.reservation.web.SessionID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WaitingController {

    private final MemberService memberService;

    @GetMapping("/waiting/add")
    public String waitingForm(Model model, HttpSession session){
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);

        if (sessionId != null){
            log.info("로그인한 회원입니다.");
            MemberDto findMember = memberService.findOneById(sessionId);
            return "basic/booking/waitingForm";
        }

        log.info("guest");
        return "basic/booking/waitingForm";
    }

}
