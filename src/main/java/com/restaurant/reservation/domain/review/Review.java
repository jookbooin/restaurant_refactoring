package com.restaurant.reservation.domain.review;

import com.restaurant.reservation.domain.Restaurant;
import com.restaurant.reservation.domain.TimeEntity;
import com.restaurant.reservation.domain.members.Member;
import com.restaurant.reservation.repository.dto.ReviewDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private String content; // 후기 
    private int grade;   // 평점
    private int viewCount;  // 조회수 - 생략 가능

//    @OneToMany(mappedBy ="review" )
//    List<ReviewFile> reviewFileList = new ArrayList<>(); // 한 리뷰 안에 파일 개수 제한시
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToMany(mappedBy ="review" , cascade = CascadeType.ALL )
    private List<File> fileList = new ArrayList<>();

    @Builder
    public Review(Long id, String content, int grade, int viewCount, Member member, Restaurant restaurant, List<File> fileList) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.viewCount = viewCount;
        this.member = member;
        this.restaurant = restaurant;
        this.fileList = fileList;
    }


    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static Review saveOf(Restaurant restaurant, Member member, ReviewDto reviewDto){
        Review review = Review.builder()
                .member(member)
                .content(reviewDto.getContent())
                .grade(reviewDto.getGrade())
                .viewCount(0)
                .build();

        restaurant.addReview(review);

        return review;
    }

    public void updateReview(ReviewDto reviewDto) {
        this.content = reviewDto.getContent();
        if(this.grade != reviewDto.getGrade()){
            this.grade = reviewDto.getGrade();
            this.restaurant.updateAverageGrade();
        }
    }
}
