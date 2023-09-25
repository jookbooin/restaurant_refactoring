package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.api.request.search.ReviewSearchRequest;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearch {
    private String sortCondition; // 정렬조건 : 좋아요 순, 등록일 순, 평점(높은/낮은) 순, 댓글 순

    @Builder
    public ReviewSearch(String sortCondition) {
        this.sortCondition = sortCondition;
    }

    public static ReviewSearch requestFrom(ReviewSearchRequest request){
        return ReviewSearch.builder()
                .sortCondition(request.getSortCondition())
                .build();
    }
}
