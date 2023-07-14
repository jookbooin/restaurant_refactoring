package com.restaurant.reservation;

import com.restaurant.reservation.domain.dto.SessionDto;
import com.restaurant.reservation.domain.enumType.MemberType;
import com.restaurant.reservation.web.SessionID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {

    /** fit 하게 sessionDto 만드는 방법 or session-id 쿼리 고민  */
    @GetMapping("/")
    public String homePage(@SessionAttribute(name = SessionID.LOGIN_MEMBER, required = false) SessionDto session, Model model) {

        log.info("sessionDto = {}",session);
        if (session == null) {
            log.info("로그인 한 상태가 아니므로 home으로 갑니다.");
            return "home";
        }

        model.addAttribute("name", session.getName());

        if(session.getMemberType().equals(MemberType.ADMIN))
            return "basic/admin/adminHome";

        return "basic/members/customerHome";
    }
}
