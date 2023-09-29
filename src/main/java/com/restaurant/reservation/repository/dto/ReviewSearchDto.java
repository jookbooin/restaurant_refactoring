package com.restaurant.reservation.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchDto {
    private Long id;
    private String content; // 후기
    private int grade;   // 평점
    private LocalDateTime createdDate;
    private Long memberId; // Member
    private String name;  // Member

    @QueryProjection
    public ReviewSearchDto(Long id, String content, int grade, LocalDateTime createdDate, Long memberId, String name) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.createdDate = createdDate;
        this.memberId = memberId;
        this.name = name;
    }
}
