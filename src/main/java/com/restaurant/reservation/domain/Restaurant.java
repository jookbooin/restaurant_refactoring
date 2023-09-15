package com.restaurant.reservation.domain;

import com.restaurant.reservation.domain.review.Review;
import com.restaurant.reservation.repository.dto.RestaurantDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    public Restaurant(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Restaurant saveOf(RestaurantDto restaurantDto){
        return Restaurant.builder()
                .name(restaurantDto.getName())
                .build();
    }

    public void addReview(Review review){
        reviewList.add(review);
        review.setRestaurant(this);
        double average = reviewList.stream().mapToDouble(Review::getGrade).average().orElse(0.0);
        this.averageGrade =  Math.round(average * 10) / 10.0;
    }
}
