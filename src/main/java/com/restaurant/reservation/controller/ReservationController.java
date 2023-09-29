package com.restaurant.reservation.controller;

import com.restaurant.reservation.domain.CategoryMenu;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.repository.dto.ReservationDto;
import com.restaurant.reservation.service.CategoryMenuService;
import com.restaurant.reservation.service.CategoryService;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.AdvancePaymentForm;
import com.restaurant.reservation.web.form.AdvanceReservationForm;
import com.restaurant.reservation.web.webDto.CategoryMenuWeb;
import com.restaurant.reservation.web.webDto.OrderMenuWeb;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final MenuService menuService;

    private final CategoryService categoryService;
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final CategoryMenuService categoryMenuService;

    final String categoryNameSpecial ="스페셜";

    @GetMapping("/reservation/advance")
    public String reservationForm(Model model){
        log.info("GET : /reservation/advance");
        model.addAttribute("form",new AdvanceReservationForm());



        String categoryCode = categoryService.findCode(categoryNameSpecial);
        List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(categoryCode);

        List<CategoryMenuWeb> wehDtoList = categoryMenuList.stream().map(cm -> CategoryMenuWeb.webFrom(cm))
                                                                       .collect(Collectors.toList());
        model.addAttribute("menuList",wehDtoList);

        return "basic/booking/advanceReservationForm";
    }




    @GetMapping("/reservation/advance/payment")
    public String advancePayGet (@ModelAttribute("advanceReservation") AdvanceReservationForm advanceReservation , Model model){

        log.info("GET : /reservation/advance/payment");
        log.info("advanceReservation toString :{} ",advanceReservation);

        List<OrderMenuWeb> orderMenuList = advanceReservation.getOrderMenuList();

        int totalPrice = Optional.ofNullable(orderMenuList)
                .map(list -> list.stream().mapToInt(o -> o.getOrderPrice()*o.getCount())
                .sum())
                .orElse(0);

//        int totalPrice = advanceFormDtoList.stream().mapToInt(o -> o.getPrice()*o.getCount()).sum();
        log.info("총 가격 : {} ",totalPrice);

        model.addAttribute("orderMenuList",orderMenuList);
        model.addAttribute("totalPrice",totalPrice);

        return "basic/booking/confirmDocument";
    }

    @PostMapping("/reservation/advance/payment")
    public String reservationPost(@Validated @ModelAttribute("form") AdvanceReservationForm advanceReservation, BindingResult bindingResult, HttpSession request, Model model, RedirectAttributes redirectAttributes){

        log.info("POST : /reservation/advance/payment");
        log.info("넘어온 객체 출력 : {}",advanceReservation);
        int menuCount = 0;

        if (advanceReservation.getOrderMenuList().size()>0) {
            menuCount = Optional.ofNullable(advanceReservation.getOrderMenuList())
                    .map(list -> list.stream().mapToInt(o -> o.getCount())
                            .sum())
                    .orElse(0);
        }

        if(advanceReservation.getNumber() == null){
            bindingResult.rejectValue("number","number.min",new Object[]{1},null);
        }else if(menuCount == 0){
            bindingResult.rejectValue("orderMenuList","orderMenuList","인원 수 만큼 주문하셔야합니다");
        }
        else if( menuCount < advanceReservation.getNumber()){
            log.info("주문 메뉴 개수: {}", advanceReservation.getOrderMenuList().size());
            log.info("신청 인원수 : : {}", advanceReservation.getNumber());
            bindingResult.rejectValue("orderMenuList","orderMenuList.menuCount",new Object[]{advanceReservation.getNumber()},null);
        }

        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);

            String categoryCode = categoryService.findCode(categoryNameSpecial);
            List<CategoryMenu> categoryMenuList = categoryMenuService.findCategoryMenu(categoryCode);

            List<CategoryMenuWeb> wehDtoList = categoryMenuList.stream().map(cm -> CategoryMenuWeb.webFrom(cm))
                    .collect(Collectors.toList());
            model.addAttribute("menuList",wehDtoList);
            return "basic/booking/advanceReservationForm";
        }

        redirectAttributes.addFlashAttribute("advanceReservation",advanceReservation);

        return "redirect:/reservation/advance/payment";
    }

    @PostMapping("/reservation/advance/add")
    public String advancePayGet (@ModelAttribute AdvancePaymentForm advancePayment , HttpSession session){
        log.info("POST : /reservation/advance/add");
        System.out.println("advancePayment 확인 = " + advancePayment);


        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);

        ReservationDto reservationDto = ReservationDto.builder()
                .date(advancePayment.getDate())
                .time(advancePayment.getTime())
                .number(advancePayment.getNumber())
                .orderMenuList(advancePayment.getOrderMenuList())
                .build();

        reservationService.addReservation(sessionId,reservationDto);

        return "redirect:/";
    }





    
}