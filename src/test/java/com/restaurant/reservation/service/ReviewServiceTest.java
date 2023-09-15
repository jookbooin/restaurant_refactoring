package com.restaurant.reservation.service;

import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.MemberRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.dto.MemberDto;
import com.restaurant.reservation.repository.dto.RestaurantDto;
import com.restaurant.reservation.repository.dto.ReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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

//    @BeforeEach
//    public void ReviewInit(){
//        RestaurantDto restaurantDto = RestaurantDto.builder().name("서초").build();
//        Restaurant restaurant = Restaurant.saveOf(restaurantDto);
//        Restaurant saveRestaurant = restaurantRepository.save(restaurant);
//
//        MemberDto memberDto1 =MemberDto.builder()
//                .email("3670lsh@naver.com")
//                .password("dltmdgjs4139!")
//                .name("고객3670")
//                .phoneNumber("01071974139")
//                .build();
//        Member member1= Member.createCustomer(memberDto1);
//
//        Member saveMember = memberRepository.save(member1);
//    }


    @Test
    @Rollback(false)
    void save_1개() {
        RestaurantDto restaurantDto = RestaurantDto.builder().name("서초").build();
        Restaurant restaurant = Restaurant.saveOf(restaurantDto);
        Restaurant saveRestaurant = restaurantRepository.save(restaurant);

        MemberDto memberDto1 =MemberDto.builder()
                .email("3670lsh@naver.com")
                .password("dltmdgjs4139!")
                .name("고객3670")
                .phoneNumber("01071974139")
                .build();
        Member member1= Member.createCustomer(memberDto1);

        Member saveMember = memberRepository.save(member1);
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<Member> memberList = memberRepository.findAll();

        assertThat(restaurantList.size()).isEqualTo(1);
        assertThat(memberList.size()).isEqualTo(1);

        em.flush();
        em.clear();
        ReviewDto reviewDto1 = ReviewDto.builder().content("헤지스").grade(5).build();
        ReviewDto reviewDto2 = ReviewDto.builder().content("무탠다드").grade(3).build();
        ReviewDto reviewDto3 = ReviewDto.builder().content("비슬로우").grade(3).build();
        reviewService.save(saveRestaurant.getId(), saveMember.getId(), reviewDto1);
        reviewService.save(saveRestaurant.getId(), saveMember.getId(), reviewDto2);
        reviewService.save(saveRestaurant.getId(), saveMember.getId(), reviewDto3);

        em.flush();
        em.clear();

        Restaurant restaurant1 = restaurantRepository.findById(1L).get();
        assertThat(restaurant1.getReviewList().size()).isEqualTo(3);

    }
}