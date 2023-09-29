package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.request.ReviewSaveRequest;
import com.restaurant.reservation.web.form.ReviewSaveForm;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long id;
    private String content; // 후기
    private Long memberId;
    private Long restaurantId;
    private int grade;   // 평점
    private int viewCount;

    @Builder
    public ReviewDto(Long id, String content, Long memberId, Long restaurantId, int grade, int viewCount) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.grade = grade;
        this.viewCount = viewCount;
    }

    public static ReviewDto of(Long rid,Long mid,ReviewSaveForm form){
        return ReviewDto.builder()
                .grade(form.getGrade())
                .content(form.getContent())
                .memberId(mid)
                .restaurantId(rid)
                .build();
    }

    public static ReviewDto of(Long rid, ReviewSaveRequest form){
        return ReviewDto.builder()
                .grade(form.getGrade())
                .content(form.getContent())
                .memberId(form.getMemberId())
                .restaurantId(rid)
                .build();
    }

}
