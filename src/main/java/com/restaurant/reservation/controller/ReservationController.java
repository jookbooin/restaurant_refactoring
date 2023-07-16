package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.dto.SessionDto;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.ReservationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    @GetMapping("reservation/add")
    public String reservationForm(Model model){
        model.addAttribute("reservation",new ReservationForm());
        return "basic/reservation/reservationForm";
    }

    @PostMapping("reservation/add")
    public String reservationPost(@ModelAttribute ReservationForm reservationForm , HttpSession request){
        SessionDto session = (SessionDto) request.getAttribute(SessionID.LOGIN_MEMBER);
//        ReservationDto
//        reservationService(session.getId())

        return "redirect:/";
    }
}
