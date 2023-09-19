package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.response.MessageResponse;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.web.form.ReviewSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/api/review/write")
    public ResponseEntity<?> reviewSave(@Validated @RequestBody ReviewSaveForm request){
        log.info("POST - /api/review/write");
        log.info("request : {}",request);

        ReviewDto reviewDto = ReviewDto.of(request);

        reviewService.save(reviewDto);

        return new ResponseEntity<>(new MessageResponse("리뷰 등록했습니다."),HttpStatus.OK);
    }




}
