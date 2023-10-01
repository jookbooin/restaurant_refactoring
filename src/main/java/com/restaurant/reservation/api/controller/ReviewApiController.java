package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.request.ReviewSaveRequest;
import com.restaurant.reservation.api.request.search.ReviewSearchRequest;
import com.restaurant.reservation.api.response.MessageResponse;
import com.restaurant.reservation.api.response.ReviewSearchResponse;
import com.restaurant.reservation.common.TwoTypeData;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.common.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping("/api/{restaurantId}/review")
    public TwoTypeData<?, ?> reivew(@PathVariable("restaurantId")Long rid, ReviewSearchRequest condition , @PageableDefault(page = 0,size = 10) Pageable pageable){

        log.info("restaurantId : {} condition : {}",rid,condition);

        ReviewSearch reviewSearch = ReviewSearch.searchFrom(condition);
        Page<ReviewSearchDto> reviewSearchPage = reviewRepository.findAllRestaurantReview(rid,reviewSearch, pageable);

        List<ReviewSearchResponse> content = reviewSearchPage.getContent()
                .stream().map(dto -> ReviewSearchResponse.responseFrom(dto))
                .collect(Collectors.toList());

        Pagination<ReviewSearchDto> pagination = new Pagination<>(reviewSearchPage);

        return new TwoTypeData<>(content,pagination);
    }

/**
 * token 방식후 memberId 정해볼 것
 * /api/{restaurantId}/{memberId}/review/write
 * /api/{restaurantId}/review/write   + session OR Token
 * */
    @PostMapping("/api/{restaurantId}/review/write")
    public ResponseEntity<?> reviewSave(@PathVariable("restaurantId") Long rid,@Validated @RequestBody ReviewSaveRequest request){
        log.info("POST - /api/review/write");
        log.info("rid : {} request : {}",rid,request);

        ReviewDto reviewDto = ReviewDto.of(rid,request);

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
