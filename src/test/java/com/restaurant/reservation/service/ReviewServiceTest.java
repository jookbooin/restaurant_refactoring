package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.ReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void ReviewInit(){

        ReviewDto reviewDto1 = ReviewDto.builder().content("헤지스").grade(5)
                .restaurantId(1L).memberId(1L).build();
        ReviewDto reviewDto2 = ReviewDto.builder().content("무탠다드").grade(3)
                .restaurantId(1L).memberId(1L).build();
        ReviewDto reviewDto3 = ReviewDto.builder().content("비슬로우")
                .grade(3).restaurantId(1L).memberId(1L).build();


        reviewService.save(reviewDto1);
        reviewService.save(reviewDto2);
        reviewService.save(reviewDto3);

    }


    @Test
    void save_1개() {

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<Member> memberList = memberRepository.findAll();

        assertThat(restaurantList.size()).isEqualTo(1);
        assertThat(memberList.size()).isEqualTo(1);


        em.flush();
        em.clear();

        Restaurant restaurant1 = restaurantRepository.findById(1L).get();
        assertThat(restaurant1.getReviewList().size()).isEqualTo(3);

    }

    @Test
    public void delete() throws Exception{

        // given

        // when
        reviewService.delete(1L,1L);

        em.flush();
        em.clear();

        System.out.println("레스토랑 확인");
        Restaurant findRestaurant = restaurantRepository.findById(1L).get();
        assertThat(findRestaurant.getReviewList().size()).isEqualTo(2);
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.size()).isEqualTo(2);
        // then
    }

    @Test
    public void update() throws Exception{
        Restaurant restaurant = null;

        // given
        ReviewDto onlyContent = ReviewDto.builder().id(1L).grade(5).content("내용 change")
                .restaurantId(1L).memberId(1L).build();
        ReviewDto withGrade = ReviewDto.builder().id(2L).content("내용 change2").grade(1)
                .restaurantId(1L).memberId(1L).build();

        /** onlyContent */
        // when
//        reviewService.update(1L,onlyContent);
//        em.flush();
//        em.clear();

//        restaurant = restaurantRepository.findById(1L).get();
//        assertThat(restaurant.getAverageGrade()).isEqualTo(3.7);

        /** withGrade */
        reviewService.update(1L,withGrade);
        em.flush();
        em.clear();

        restaurant = restaurantRepository.findById(1L).get();
        assertThat(restaurant.getAverageGrade()).isEqualTo(3.0);

    }


}