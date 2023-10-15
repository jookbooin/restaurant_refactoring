package com.restaurant.reservation.repository;

import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.*;
import com.restaurant.reservation.service.ReviewService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {



    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Profile("test")
    @BeforeEach
    public void ReviewInit(){
        /** 1. Member - 고객 / 관리자 */
            MemberDto memberDto = MemberDto.builder()
                    .email("3670lsh@naver.com")
                    .password("dltmdgjs4139!")
                    .name("고객3670")
                    .phoneNumber("01071974139")
                    .build();
            Member member= Member.createCustomer(memberDto);
            memberRepository.save(member);

        RestaurantDto restaurantDto = RestaurantDto.builder().name("식당").build();
        Restaurant restaurant = restaurantRepository.save(Restaurant.saveOf(restaurantDto));

        Random rand = new Random();
        for(int i = 0; i<100; i++) {

            ReviewDto reviewDto = ReviewDto.builder()
                    .grade((rand.nextInt(5) + 1))
                    .content("20자 테스트중입니다 "+i)
                    .restaurantId(1L)
                    .memberId(1L)
                    .build();
            reviewService.save(reviewDto);
        }
    }

    @Test
    public void findAllRestaurantReview_테스트() throws Exception{

        ReviewSearch searchCondition = new ReviewSearch("등록일 순");
        PageRequest pageable = PageRequest.of(4,10);
        // given
        Page<ReviewSearchDto> restaurantReviews = reviewRepository.findAllRestaurantReview(1L, searchCondition, pageable);
        Assertions.assertThat(restaurantReviews.getSize()).isEqualTo(10);
        for (ReviewSearchDto reviewSearchDto : restaurantReviews.getContent()) {
            System.out.println("reviewSearchDto = " + reviewSearchDto);
        }
        // when

        // then
    }

}