package com.restaurant.reservation.api.request.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
public class ReviewSaveRequest {
    @NotNull(message = "평점을 선택해주세요.")
    private Integer grade;

    @NotBlank
    @Size(min = 20 ,message = "20자 이상 작성해야합니다")
    private String content;
    private Long memberId;
    private Long restaurantId;
}
