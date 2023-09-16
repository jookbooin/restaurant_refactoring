package com.restaurant.reservation.repository.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long id;
    private String content; // 후기
    private int grade;   // 평점
    private int viewCount;

    @Builder
    public ReviewDto(Long id, String content, int grade, int viewCount) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.viewCount = viewCount;
    }
}
