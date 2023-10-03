package com.restaurant.reservation.controller;

import com.restaurant.reservation.api.request.search.ReviewSearchRequest;
import com.restaurant.reservation.common.Pagination;
import com.restaurant.reservation.common.TwoTypeData;
import com.restaurant.reservation.common.exception.domain.FileException;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import com.restaurant.reservation.service.FileHandler;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.web.SessionID;
import com.restaurant.reservation.web.form.ReviewSaveForm;
import com.restaurant.reservation.web.webDto.ReviewSearchWeb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final FileHandler fileHandler;

    public final Long restaurantId = 1L;

    @GetMapping("/{restaurantId}/review")
    public String list(@PathVariable("restaurantId") Long rid, ReviewSearchRequest condition,
                       @PageableDefault(page = 0,size = 10) Pageable pageable, Model model){

        log.info("GET - /{}/review",rid);
        log.info("condition : {}",condition);

        ReviewSearch reviewSearch = ReviewSearch.searchFrom(condition);

        TwoTypeData<List<ReviewSearchDto>, Pagination<ReviewSearchDto>> dataResponse = reviewService.reviewSearch(rid, reviewSearch, pageable);

        List<ReviewSearchWeb> reviewList = dataResponse.getData1().stream().map(dto -> ReviewSearchWeb.webFrom(dto))
                .collect(Collectors.toList());
        log.info("review size : {}",reviewList.size());
        Pagination<ReviewSearchDto> pagination = dataResponse.getData2();

        model.addAttribute("restaurantId",rid);
        model.addAttribute("reviewList",reviewList);
        model.addAttribute("pagination",pagination);

        return "basic/board/review";
    }


    /** interceptor 나 security로 sessionId 있을때만 들어갈 수 있도록 설정*/
    @GetMapping("/{restaurantId}/review/write")
    public String reviewWrite(@PathVariable("restaurantId") Long rid, @ModelAttribute("form") ReviewSaveForm form,
                                            HttpSession session, Model model){

        log.info("GET - /{}/review/write",rid);
        model.addAttribute("restaurantId",rid);
        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
        log.info("sessionId : {}",sessionId);

        return "basic/board/reviewWrite";
    }

    @PostMapping("/{restaurantId}/review/write")
    public String reviewSave(@PathVariable("restaurantId") Long rid,@Validated @ModelAttribute("form") ReviewSaveForm form, BindingResult bindingResult,
                             Model model,HttpSession session) throws IOException {
        log.info("POST - /{}/review/write",rid);
        log.info("form : {}",form);

        /** 파일 형식 only jpg/png */
        List<MultipartFile> multipartFileList =null;
        try {
            multipartFileList = fileHandler.imageContentType(form.getMultipartFileList());
        } catch (FileException e) {
            bindingResult.rejectValue("multipartFileList","MultipartFile.ContentType",e.getMessage());
        }

        if (bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return "basic/board/reviewWrite";
        }

        Long sessionId = (Long) session.getAttribute(SessionID.LOGIN_MEMBER);
//
        ReviewDto reviewDto = ReviewDto.saveOf(rid, sessionId, form);
//
        reviewService.save(reviewDto);

        return "redirect:/"+rid+"/review";
    }

}
