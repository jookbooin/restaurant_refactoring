package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.repository.dto.ReviewDto;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.exception.MemberException;
import com.restaurant.reservation.exception.RestaurantException;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
