package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.dto.MenuDto;
import com.restaurant.reservation.domain.dto.ReservationDto;
import com.restaurant.reservation.domain.enumType.TimeEnum;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.AdvancePaymentForm;
import com.restaurant.reservation.web.form.AdvanceReservationForm;
import com.restaurant.reservation.web.webDto.OrderMenuWebDto;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final MenuService menuService;

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @GetMapping("/reservation/advance")
    public String reservationForm(Model model){
        log.info("GET : /reservation/advance");
        model.addAttribute("form",new AdvanceReservationForm());
        List<MenuDto> specialMenuList = menuService.findSpecialMenu();
        model.addAttribute("menuList",specialMenuList);

        return "basic/reservation/advanceReservationForm";
    }


    @PostMapping("/reservation/advance/payment")
    public String reservationPost(@Validated @ModelAttribute("form") AdvanceReservationForm advanceReservation, BindingResult bindingResult, HttpSession request, Model model, RedirectAttributes redirectAttributes){

        log.info("POST : /reservation/advance/payment");
        log.info("넘어온 객체 출력 : {}",advanceReservation);

        int totalCount = Optional.ofNullable(advanceReservation.getOrderMenuList())
                        .map(list -> list.stream().mapToInt(o -> o.getCount())
                        .sum())
                        .orElse(0);

        if( totalCount < advanceReservation.getNumber() && totalCount >= 0 ){
            log.info("주문 메뉴 개수: {}", advanceReservation.getOrderMenuList().size());
            log.info("신청 인원수 : : {}", advanceReservation.getNumber());
            bindingResult.rejectValue("orderMenuList","listSize");
        }

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

        log.info("GET : /reservation/advance/payment");
        log.info("advanceReservation toString :{} ",advanceReservation);

        List<OrderMenuWebDto> orderMenuList = advanceReservation.getOrderMenuList();

        int totalPrice = Optional.ofNullable(orderMenuList)
                .map(list -> list.stream().mapToInt(o -> o.getCount()*o.getPrice())
                .sum())
                .orElse(0);

//        int totalPrice = advanceFormDtoList.stream().mapToInt(o -> o.getPrice()*o.getCount()).sum();
        log.info("총 가격 : {} ",totalPrice);

        model.addAttribute("orderMenuList",orderMenuList);
        model.addAttribute("totalPrice",totalPrice);

        return "basic/reservation/confirmDocument";
    }

    @PostMapping("/reservation/advance/add")
    public String advancePayGet (@ModelAttribute AdvancePaymentForm advancePayment ,BindingResult bindingResult, HttpSession session){
        log.info("POST : /reservation/advance/add");
        System.out.println("advancePayment 확인 = " + advancePayment);
        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);

            return "basic/reservation/confirmDocument";
        }

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);

        ReservationDto reservationDto = ReservationDto.builder()
                .date(LocalDate.parse(advancePayment.getDate()))
                .time(TimeEnum.transferStringToTime(advancePayment.getTime()))
                .number(advancePayment.getNumber())
                .orderMenuList(advancePayment.getOrderMenuList())
                .build();

        reservationService.addReservation(sessionId,reservationDto);

        return "redirect:/";
    }





    
}