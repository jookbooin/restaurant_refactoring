package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.AdvanceForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final MenuService menuService;

    private final ReservationService reservationService;
    @GetMapping("reservation/advance")
    public String reservationForm(Model model){
        model.addAttribute("form",new AdvanceForm());
        List<MenuDto> specialMenuList = menuService.findSpecialMenu();
        model.addAttribute("menuList",specialMenuList);

        return "basic/reservation/advanceReservationForm";
    }

    @PostMapping("reservation/advance/add")
    public String reservationPost(@Validated @ModelAttribute("form") AdvanceForm advanceForm, BindingResult bindingResult, HttpSession request,Model model){

        if (bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);
            List<MenuDto> specialMenuList = menuService.findSpecialMenu();
            model.addAttribute("menuList",specialMenuList);
            return "basic/reservation/advanceReservationForm";
        }

        Long sessionId = (Long) request.getAttribute(SessionID.LOGIN_MEMBER);
        System.out.println("advanceForm = " + advanceForm);

        LocalDate localDate = LocalDate.parse(advanceForm.getDate());
        LocalTime localTime = LocalTime.parse(advanceForm.getTime());

        System.out.println("advanceForm.getTime() = " + advanceForm.getTime());
        System.out.println("advanceForm.getDate() = " + advanceForm.getDate());

        System.out.println("localDate.getClass() = " + localDate.getClass());
        System.out.println("localTime.getClass() = " + localTime.getClass());

        return "redirect:/";
    }




}
