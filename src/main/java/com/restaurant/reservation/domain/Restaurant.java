package com.restaurant.reservation.domain;

import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.repository.dto.RestaurantDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    private Long id;
    private String name;

    private double averageGrade;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Restaurant(Long id, String name, double averageGrade, List<Review> reviewList) {
        this.id = id;
        this.name = name;
        this.averageGrade = averageGrade;
        if(reviewList!= null && !reviewList.isEmpty())
        this.reviewList = reviewList;
    }

    public static Restaurant saveOf(RestaurantDto restaurantDto){
        return Restaurant.builder()
                .name(restaurantDto.getName())
                .build();
    }
    public void addReview(Review review){
//        log.info("리뷰값 : {}",review.getGrade());
        reviewList.add(review);
        review.setRestaurant(this);
        updateAverageGrade();
    }

    public void updateAverageGrade() {

        double average = reviewList.stream().mapToDouble(Review::getGrade).average().orElse(0.0);
        this.averageGrade =  Math.round(average * 10) / 10.0;
//        log.info("입력 값 : {} 레스토랑 평균 : {}",average,this.averageGrade);
    }

    public void deleteReview(Review review){
        reviewList.remove(review);
        updateAverageGrade();
    }

}
