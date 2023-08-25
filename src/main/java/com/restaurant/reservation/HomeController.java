package com.restaurant.reservation;

import com.restaurant.reservation.repository.dto.MemberDto;
import com.restaurant.reservation.domain.enumType.MemberRole;
import com.restaurant.reservation.service.MemberService;
import com.restaurant.reservation.web.SessionID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    
    private final MemberService memberService;
    /** fit 하게 sessionDto 만드는 방법 or session-id 쿼리 고민  */
    @GetMapping("/")
//    public String homePage(@SessionAttribute(name = SessionID.LOGIN_MEMBER, required = false) SessionDto session, Model model) {
    public String homePage(@SessionAttribute(name = SessionID.LOGIN_MEMBER, required = false) Long sessionId, Model model) {

        MemberDto findMember = memberService.findOneById(sessionId);
//        log.info("sessionDto = {}",session);

        if (findMember == null) {
            log.info("로그인 한 상태가 아니므로 home으로 갑니다.");
            return "home";
        }

        model.addAttribute("name", findMember.getName());


        if(findMember.getMemberRole().equals(MemberRole.ADMIN))
            return "basic/admin/adminHome";

        return "basic/members/customerHome";
    }
}
