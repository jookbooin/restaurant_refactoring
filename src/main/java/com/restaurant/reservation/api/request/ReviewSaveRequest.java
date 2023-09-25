package com.restaurant.reservation.api.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSaveRequest {
    @NotNull(message = "평점을 선택해주세요.")
    private Integer grade;
    @NotNull
    private Long memberId;

    @Size(min = 20,  message ="20자 이상 작성해야합니다")
    private String content;


}
