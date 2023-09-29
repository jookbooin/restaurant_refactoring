package com.restaurant.reservation.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchResponse {

    private Long id;
    private String content; // 후기
    private int grade;   // 평점
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdDate;
    private Long memberId; // Member
    private String name;  // Member

    @Builder
    public ReviewSearchResponse(Long id, String content, int grade, LocalDateTime createdDate, Long memberId, String name) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.createdDate = createdDate;
        this.memberId = memberId;
        this.name = name;
    }

    public static ReviewSearchResponse responseFrom(ReviewSearchDto dto){
        return ReviewSearchResponse.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .grade(dto.getGrade())
                .createdDate(dto.getCreatedDate())
                .memberId(dto.getMemberId())
                .name(dto.getName())
                .build();
    }

}
