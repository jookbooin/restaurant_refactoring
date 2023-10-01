package com.restaurant.reservation.service;

import com.restaurant.reservation.common.TwoTypeData;
import com.restaurant.reservation.common.Pagination;
import com.restaurant.reservation.common.exception.domain.MemberException;
import com.restaurant.reservation.common.exception.domain.RestaurantException;
import com.restaurant.reservation.common.exception.domain.ReviewException;
import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.repository.dto.ReviewSearch;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;


    /**
     * 23_10_02
     * 1. jpa의 Page객체를 Service쪽에 숨기는게 더 나을지도 모르겠다고 생각이들었다.
     * 2. content 와 Pagination을 감싸 Controller로 반환한다.
     * */
    public TwoTypeData<List<ReviewSearchDto>, Pagination<ReviewSearchDto>> reviewSearch(Long rid, ReviewSearch reviewSearch , Pageable pageable){

        Page<ReviewSearchDto> reviewSearchPage = reviewRepository.findAllRestaurantReview(rid,reviewSearch, pageable);

        Pagination<ReviewSearchDto> pagination = new Pagination<>(reviewSearchPage);

        return new TwoTypeData<>(reviewSearchPage.getContent(),pagination);
    }
    @Transactional
    public Long save(ReviewDto reviewDto){
        Restaurant restaurant = restaurantRepository.findById(reviewDto.getRestaurantId()).orElseThrow(() -> new RestaurantException("레스토랑이 존재하지 않습니다"));
        Member member = memberRepository.findById(reviewDto.getMemberId()).orElseThrow(() -> new MemberException("해당 회원이 존재하지않습니다"));
        Review review = Review.saveOf(restaurant, member, reviewDto);
        Review saveReview = reviewRepository.save(review);
        return saveReview.getId();
    }

    @Transactional
    public void delete(Long mid ,Long rid){

//        Review findReview = reviewRepository.findFech(rid).orElseThrow(() -> new ReviewException("해당 id의 리뷰가 존재하지 않습니다"));
        Review findReview = reviewRepository.findFech(rid).orElseThrow(() -> new ReviewException("해당 id의 리뷰가 존재하지 않습니다"));
        if(findReview.getMember().getId().equals(mid)){
            log.info("해당 리뷰 작성자가 맞음");

            findReview.getRestaurant().deleteReview(findReview);
            reviewRepository.delete(findReview);

        }else{
            throw new ReviewException("해당 작성자가 쓴 리뷰가 아닙니다");
        }
    }

    @Transactional
    public void update(Long mid , ReviewDto reviewDto){

        Review findReview = reviewRepository.findFech(reviewDto.getId()).orElseThrow(() -> new ReviewException("해당 id의 리뷰가 존재하지 않습니다"));
        if(findReview.getMember().getId().equals(mid)) {
            log.info("해당 리뷰 작성자가 맞음");
            findReview.updateReview(reviewDto);
        }else{
            throw new ReviewException("해당 작성자가 쓴 리뷰가 아닙니다");
        }
    }


}
