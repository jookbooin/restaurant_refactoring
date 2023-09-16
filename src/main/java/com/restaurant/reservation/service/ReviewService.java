package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.exception.MemberException;
import com.restaurant.reservation.exception.RestaurantException;
import com.restaurant.reservation.exception.ReviewException;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long rid ,Long mid , ReviewDto reviewDto){
        Restaurant restaurant = restaurantRepository.findById(rid).orElseThrow(() -> new RestaurantException("레스토랑이 존재하지 않습니다"));
        Member member = memberRepository.findById(mid).orElseThrow(() -> new MemberException("해당 회원이 존재하지않습니다"));
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
