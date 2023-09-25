package com.restaurant.reservation.web.form;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuUpdateForm {
    @NotBlank(message = "카테고리를 선택해야합니다.")
    private String categoryName;
    @NotBlank(message="메뉴 이름을 등록해야합니다.")
    private String menuName;
    @NotNull(message="가격을 입력해야합니다.")
    private Integer menuPrice;
    private String description;
}
