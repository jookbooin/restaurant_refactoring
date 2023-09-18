package com.restaurant.reservation.web.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSaveForm {

    @NotNull(message = "평점을 선택해주세요.")
    private Integer grade;
    private Long memberId;
    private Long restaurantId;

    @NotBlank
    @Size(min = 20,  message ="20자 이상 작성해야합니다")
    private String content;


    public ReviewSaveForm(Long memberId, Long restaurantId) {
        this.memberId = memberId;
        this.restaurantId = restaurantId;
    }

}
