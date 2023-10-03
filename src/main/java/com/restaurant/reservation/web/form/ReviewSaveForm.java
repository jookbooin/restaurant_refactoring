package com.restaurant.reservation.web.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/** 1. 우선 reviewController에 사용 */
@Getter
@Setter
@ToString
public class ReviewSaveForm {

    @NotNull(message = "평점을 선택해주세요.")
    private Integer grade;

    @Size(min = 20,  message ="20자 이상 작성해야합니다")
    private String content;

    private List<MultipartFile> multipartFileList ;

    public ReviewSaveForm() {
    }

}
