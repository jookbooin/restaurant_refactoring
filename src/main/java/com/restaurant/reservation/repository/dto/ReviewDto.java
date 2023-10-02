package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.web.form.ReviewSaveForm;
import com.restaurant.reservation.web.form.ReviewUpdateForm;
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

    public static ReviewDto saveOf(Long rtid, Long mid, ReviewSaveForm form){
        return ReviewDto.builder()
                .grade(form.getGrade())
                .content(form.getContent())
                .memberId(mid)
                .restaurantId(rtid)
                .build();
    }

    public static ReviewDto updateOf(Long rtid, Long mid,Long rwid, ReviewUpdateForm form){
        return ReviewDto.builder()
                .id(rwid)
                .grade(form.getGrade())
                .content(form.getContent())
                .memberId(mid)
                .restaurantId(rtid)
                .build();
    }

//    public static ReviewDto of(Long rid, ReviewSaveRequest form){
//        return ReviewDto.builder()
//                .grade(form.getGrade())
//                .content(form.getContent())
//                .memberId(form.getMemberId())
//                .restaurantId(rid)
//                .build();
//    }

}
