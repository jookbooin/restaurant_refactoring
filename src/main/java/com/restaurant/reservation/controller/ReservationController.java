package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.domain.dto.ReservationDto;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.AdvancePaymentForm;
import com.restaurant.reservation.web.form.AdvanceReservationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.restaurant.reservation.web.form.AdvanceReservationForm.advanceFormDto;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final MenuService menuService;

    private final ReservationService reservationService;
    @GetMapping("/reservation/advance")
    public String reservationForm(Model model){
        model.addAttribute("form",new AdvanceReservationForm());
        List<MenuDto> specialMenuList = menuService.findSpecialMenu();
        model.addAttribute("menuList",specialMenuList);

        return "basic/reservation/advanceReservationForm";
    }


    @PostMapping("/reservation/advance/payment")
    public String reservationPost(@Validated @ModelAttribute("form") AdvanceReservationForm advanceReservation, BindingResult bindingResult, HttpSession request, Model model, RedirectAttributes redirectAttributes){

        System.out.println("넘어온 객체 출력 = " + advanceReservation);
        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);
            List<MenuDto> specialMenuList = menuService.findSpecialMenu();
            model.addAttribute("menuList",specialMenuList);
            return "basic/reservation/advanceReservationForm";
        }


        redirectAttributes.addFlashAttribute("advanceReservation",advanceReservation);

        return "redirect:/reservation/advance/payment";
    }

    @GetMapping("/reservation/advance/payment")
    public String advancePayGet (@ModelAttribute("advanceReservation") AdvanceReservationForm advanceReservation , Model model){

        System.out.println("get - reservation/advance/payment 로 와썽");
        System.out.println("advanceReservation 확인 = " + advanceReservation);
        // price
        // menu name

        List<advanceFormDto> advanceFormDtoList = advanceReservation.getOrderMenuList();
        int totalPrice = advanceFormDtoList.stream().mapToInt(o -> o.getPrice()*o.getCount()).sum();
        System.out.println("totalPrice = " + totalPrice);

        model.addAttribute("orderMenuList",advanceFormDtoList);
        model.addAttribute("totalPrice",totalPrice);

        return "basic/reservation/confirmDocument";
    }

    @PostMapping("/reservation/advance/add")
    public String advancePayGet (@ModelAttribute AdvancePaymentForm advancePayment ,BindingResult bindingResult, HttpSession request){

        System.out.println("advancePayment 확인 = " + advancePayment);
        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);

            return "basic/reservation/confirmDocument";
        }

        Long sessionId = (Long) request.getAttribute(SessionID.LOGIN_MEMBER);


        ReservationDto reservationDto = ReservationDto.builder()
                .date(LocalDate.parse(advancePayment.getDate()))
                .time(LocalTime.parse(advancePayment.getTime()))
                .number(advancePayment.getNumber())
                .orderMenuList(advancePayment.getOrderMenuList())
                .build();

        reservationService.addReservation(sessionId,reservationDto);

        return "redirect:/";
    }

    
}