package com.restaurant.reservation.controller;

import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.ReviewSaveForm;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    public final Long restaurantId = 1L;

    @GetMapping("/review")
    public String list(){
        log.info("GET - /review");
        return "basic/board/review";
    }


    @GetMapping("/review/write")
    public String reviewWrite(HttpSession session, Model model){

        log.info("GET - /review/write");
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
        model.addAttribute("form",new ReviewSaveForm(sessionId,restaurantId));

        return "basic/board/reviewWrite";
    }

    @PostMapping("/review/write")
    public String reviewSave(@Validated @ModelAttribute("form") ReviewSaveForm form, BindingResult bindingResult,Model model,HttpSession session){
        log.info("POST - /review/write");
        log.info("form : {}",form);

        if (bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return "basic/board/reviewWrite";
        }

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);

        ReviewDto reviewDto = ReviewDto.builder()
                .grade(form.getGrade())
                .content(form.getContent())
                .restaurantId(form.getRestaurantId())
                .memberId(sessionId)
                .build();

        reviewService.save(reviewDto);
        return "redirect:/review";
    }
}
