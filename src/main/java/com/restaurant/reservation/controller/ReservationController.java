package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.AdvanceReservationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final MenuService menuService;

    private final ReservationService reservationService;
    @GetMapping("/reservation/advance")
    public String reservationForm(Model model){
//        model.addAttribute("form",new AdvanceForm());
        List<MenuDto> specialMenuList = menuService.findSpecialMenu();
        model.addAttribute("menuList",specialMenuList);

        return "basic/reservation/advanceReservationForm";
    }

    @ResponseBody
    @PostMapping("/reservation/advance/add")
    public String reservationPost(@Validated @RequestBody AdvanceReservationForm advanceReservation, BindingResult bindingResult, HttpSession request, Model model, RedirectAttributes redirectAttributes){

        System.out.println("넘어온 객체 출력 = " + advanceReservation);

        Long sessionId = (Long) request.getAttribute(SessionID.LOGIN_MEMBER);

//        ReservationDto reservationDto = ReservationDto.builder()
//                .date(LocalDate.parse(advanceForm.getDate()))
//                .time(LocalTime.parse(advanceForm.getTime()))
//                .number(advanceForm.getNumber())
//                .orderMenuList(advanceForm.getOrderMenuList())
//                .build();
//        reservationService.addReservation(sessionId,reservationDto);
        redirectAttributes.addFlashAttribute("advanceReservation",advanceReservation);

        return "redirect:/reservation/advance/payment";
    }

    @GetMapping("/reservation/advance/payment")
    public String advancePayGet (@ModelAttribute("advanceReservation") AdvanceReservationForm advanceReservation , Model model){

        System.out.println("get - reservation/advance/payment 로 와썽");
        System.out.println("advanceReservation = " + advanceReservation);


        int totalPrice = advanceReservation.getOrderMenuList().stream().mapToInt(o -> o.getPrice()).sum();
        model.addAttribute("document",advanceReservation);
        model.addAttribute("totalPrice",totalPrice);
        return "basic/reservation/confirmDocument";
    }

    
}