package com.restaurant.reservation.web.webDto;

import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchWeb {

    private Long id;
    private String content; // 후기

    private int grade;   // 평점
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdDate;
    private Long memberId; // Member
    private String name;  // Member

    @Builder
    public ReviewSearchWeb(Long id, String content, int grade, LocalDateTime createdDate, Long memberId, String name) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.createdDate = createdDate;
        this.memberId = memberId;
        this.name = name;
    }

    public static ReviewSearchWeb webFrom(ReviewSearchDto dto){
        return ReviewSearchWeb.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .grade(dto.getGrade())
                .createdDate(dto.getCreatedDate())
                .memberId(dto.getMemberId())
                .name(dto.getName())
                .build();
    }

}
