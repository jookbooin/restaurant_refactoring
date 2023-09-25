package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.request.search.ReviewSearchRequest;
import com.restaurant.reservation.api.response.MessageResponse;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.web.form.ReviewSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping("/api/review")
    public ResponseEntity<?> reivew(ReviewSearchRequest condition , @PageableDefault(page = 0,size = 10) Pageable pageable){

        log.info("condition : {}",condition);

        ReviewSearch reviewSearch = ReviewSearch.requestFrom(condition);
        Page<ReviewSearchDto> allReview = reviewRepository.findAllReview(reviewSearch, pageable);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/review/write")
    public ResponseEntity<?> reviewSave(@Validated @RequestBody ReviewSaveForm request){
        log.info("POST - /api/review/write");
        log.info("request : {}",request);

        ReviewDto reviewDto = ReviewDto.of(request);

        reviewService.save(reviewDto);

        return new ResponseEntity<>(new MessageResponse("리뷰 등록했습니다."),HttpStatus.OK);
    }

//    @PostMapping("/api/review/write2")
//    public ResponseEntity<?> reviewSave(@Validated @RequestBody ReviewSaveForm request, BindingResult bindingResult) throws BindException {
//        log.info("POST - /api/review/write");
//        log.info("request : {}",request);
//        if (bindingResult.hasErrors()){
//            log.info("검증 오류 발생 errors = {}", bindingResult);
//            throw new BindException(bindingResult);
//        }
//
//        ReviewDto reviewDto = ReviewDto.of(request);
//
//        reviewService.save(reviewDto);
//
//        return new ResponseEntity<>(new MessageResponse("리뷰 등록했습니다."),HttpStatus.OK);
//    }





}
