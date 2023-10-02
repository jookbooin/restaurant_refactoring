package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Restaurant;
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

        ReviewDto reviewDto1 = ReviewDto.builder().content("헤지스헤지스헤지스헤지스헤지스헤지스헤지스헤지스헤지스")
                .grade(5).restaurantId(1L).memberId(1L).build();
        ReviewDto reviewDto2 = ReviewDto.builder().content("무탠다드무탠다드무탠다드무탠다드무탠다드무탠다드무탠다드")
                .grade(3).restaurantId(1L).memberId(1L).build();
        ReviewDto reviewDto3 = ReviewDto.builder().content("카키스카키스카키스카키스카키스카키스카키스카키스카키스")
                .grade(3).restaurantId(1L).memberId(1L).build();

        reviewService.save(reviewDto1);
        reviewService.save(reviewDto2);
        reviewService.save(reviewDto3);

    }


    @Test
    void save_1개() {

        em.flush();
        em.clear();
        /** restaurant1.getAverageGrade() == 3.7  <= 11 / 3 = 3.666 */

        ReviewDto reviewDto4 = ReviewDto.builder().content("홀리선홀리선홀리선홀리선홀리선홀리선홀리선홀리선")
                .grade(5).restaurantId(1L).memberId(1L).build();
        reviewService.save(reviewDto4);

        em.flush();
        em.clear();


        Restaurant restaurant1 = restaurantRepository.findById(1L).get();
        assertThat(restaurant1.getReviewList().size()).isEqualTo(4);
        assertThat(restaurant1.getAverageGrade()).isEqualTo(4.0);

    }

    @Test
    public void delete() throws Exception{

        // given
        ReviewDto reviewDto4 = ReviewDto.builder().content("홀리선홀리선홀리선홀리선홀리선홀리선홀리선홀리선")
                .grade(5).restaurantId(1L).memberId(1L).build();
        reviewService.save(reviewDto4);

        em.flush();
        em.clear();

        // when
        reviewService.delete(1L,1L,4L);

        em.flush();
        em.clear();

        System.out.println("레스토랑 확인");
        Restaurant findRestaurant = restaurantRepository.findById(1L).get();
        assertThat(findRestaurant.getReviewList().size()).isEqualTo(3);
        assertThat(findRestaurant.getAverageGrade()).isEqualTo(3.7);

        em.flush();
        em.clear();
        System.out.println("리뷰 개수 확인");
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList.size()).isEqualTo(3);
        // then
    }

    @Test
    public void update() throws Exception{

        ReviewDto reviewDto4 = ReviewDto.builder().content("홀리선홀리선홀리선홀리선홀리선홀리선홀리선홀리선")
                .grade(5).restaurantId(1L).memberId(1L).build();
        reviewService.save(reviewDto4);

        em.flush();
        em.clear();

        // given
        ReviewDto onlyContent = ReviewDto.builder().id(4L).content("내용 change")
                .restaurantId(1L).memberId(1L).build();

        ReviewDto withGrade = ReviewDto.builder().id(4L).restaurantId(1L).memberId(1L)
                .grade(1)
                .content("content With Grade content With Grade content With Grade content With Grade content With Grade content With Grade").grade(1)
                .build();

        /** onlyContent */
        // when
//        reviewService.update(1L,onlyContent);
//        em.flush();
//        em.clear();

//        restaurant = restaurantRepository.findById(1L).get();
//        assertThat(restaurant.getAverageGrade()).isEqualTo(3.7);

        /** withGrade */
        reviewService.update(withGrade);

        em.flush();
        em.clear();

        Restaurant restaurant  = restaurantRepository.findById(1L).get();
        assertThat(restaurant.getAverageGrade()).isEqualTo(3.0);

    }


}