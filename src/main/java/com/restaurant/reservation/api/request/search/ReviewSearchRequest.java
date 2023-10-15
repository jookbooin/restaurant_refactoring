package com.restaurant.reservation.api.request.search;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchRequest {
    private String sortCondition; // 정렬조건 : 좋아요 순, 등록일 순, 평점(높은/낮은) 순, 댓글 순

}
