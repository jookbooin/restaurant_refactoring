package com.restaurant.reservation.repository.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long id;
    private String content; // 후기
    private Integer grade;   // 평점
    private Integer viewCount;

    @Builder
    public ReviewDto(Long id, String content, Integer grade, Integer viewCount) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.viewCount = viewCount;
    }
}
