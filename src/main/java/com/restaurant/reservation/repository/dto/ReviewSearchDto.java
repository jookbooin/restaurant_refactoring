package com.restaurant.reservation.repository.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchDto {
    private Long id;
    private String content; // 후기
    private Long memberId;
    private Long restaurantId;
    private double grade;   // 평점
    private LocalDate createdDate;

    @Builder
    public ReviewSearchDto(Long id, String content, Long memberId, Long restaurantId, double grade, LocalDate createdDate) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.grade = grade;
        this.createdDate = createdDate;
    }
}
