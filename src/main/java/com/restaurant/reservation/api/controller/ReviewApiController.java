package com.restaurant.reservation.api.controller;

import com.restaurant.reservation.api.request.search.ReviewSearchRequest;
import com.restaurant.reservation.api.response.MessageResponse;
import com.restaurant.reservation.api.response.ReviewSearchResponse;
import com.restaurant.reservation.common.Pagination;
import com.restaurant.reservation.common.TwoTypeData;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import com.restaurant.reservation.service.FileStore;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.web.form.ReviewSaveForm;
import com.restaurant.reservation.web.form.ReviewUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final FileStore fileStore;

    /** 추후에 삭제할 것*/
    private final Long memberId = 1L;

    @GetMapping("/api/{restaurantId}/review")
    public TwoTypeData<?, ?> reivew(@PathVariable("restaurantId")Long rtid, ReviewSearchRequest condition , @PageableDefault(page = 0,size = 10) Pageable pageable){

        log.info("restaurantId : {} condition : {}",rtid,condition);

        ReviewSearch reviewSearch = ReviewSearch.searchFrom(condition);

        TwoTypeData<List<ReviewSearchDto>, Pagination<ReviewSearchDto>> twoTypeData = reviewService.reviewSearch(rtid, reviewSearch, pageable);

        List<ReviewSearchResponse> content = twoTypeData.getData1().stream().map(dto ->new ReviewSearchResponse(dto))
                .collect(Collectors.toList());

        Pagination<ReviewSearchDto> pagination = twoTypeData.getData2();

        return new TwoTypeData<>(content,pagination);
    }


    @GetMapping("/api/{reviewId}/images/{filename}")
    public Resource downloadImage(@PathVariable Long reviewId,@PathVariable String filename) throws MalformedURLException {

       log.info("/api/{}/images/{}",reviewId,filename);

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

/**
 * token 방식후 memberId 정해볼 것
 * /api/{restaurantId}/{memberId}/review/write
 * /api/{restaurantId}/review/write   + session OR Token
 * */
    @PostMapping("/api/{restaurantId}/review/write")
    public ResponseEntity<?> reviewSave(@PathVariable("restaurantId") Long rtid,@Validated @RequestBody ReviewSaveForm request) throws IOException {
        log.info("POST - /api/review/write");
        log.info("restaurantId : {} request : {}",rtid,request);

        ReviewDto reviewDto = ReviewDto.saveOf(rtid,memberId,request);

        reviewService.save(reviewDto);

        return new ResponseEntity<>(new MessageResponse("리뷰 등록했습니다."),HttpStatus.OK);
    }

    @DeleteMapping("/api/{restaurantId}/review/{reviewId}/delete")
    public ResponseEntity<?> reviewDelete(@PathVariable("restaurantId") Long rtid,@PathVariable("reviewId") Long rwid){
        log.info("Delete - /api/{}/review/{}/delete",rtid,rwid);

        reviewService.delete(memberId,rtid,rwid);
        return new ResponseEntity<>(new MessageResponse("리뷰 삭제했습니다."),HttpStatus.OK);
    }

    @PatchMapping("/api/{restaurantId}/review/{reviewId}/update")
    public ResponseEntity<?> reviewUpdate(@PathVariable("restaurantId") Long rtid, @PathVariable("reviewId") Long rwid,
                                          @Validated @RequestBody ReviewUpdateForm request){
        log.info("Patch - /api/{}/review/{}/update",rtid,rwid);
        log.info("ReviewUpdateForm : {}",request);

        ReviewDto reviewDto = ReviewDto.updateOf(rtid,memberId,rwid,request);
        reviewService.update(reviewDto);
        return new ResponseEntity<>(new MessageResponse("리뷰 수정했습니다."),HttpStatus.OK);
    }

//    @GetMapping()


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
