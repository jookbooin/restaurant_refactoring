package com.restaurant.reservation.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurant.reservation.repository.dto.ReviewSearchDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchResponse {

    private Long id;
    private String content; // 후기
    private int grade;   // 평점
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdDate;
    private Long memberId; // Member
    private String name;  // Member

    List<ImageResponse> imageResponseList = new ArrayList<>();

    public ReviewSearchResponse(ReviewSearchDto dto) {
        this.id = dto.getId();
        this.content = dto.getContent();
        this.grade = dto.getGrade();
        this.createdDate = dto.getCreatedDate();
        this.memberId = dto.getMemberId();
        this.name = dto.getName();

        if(!dto.getUploadFileDtoList().isEmpty()){
            List<ImageResponse> imageResponseList = dto.getUploadFileDtoList().stream()
                    .map(uploadFileDto -> ImageResponse.responseFrom(uploadFileDto))
                    .collect(Collectors.toList());
            this.imageResponseList =imageResponseList;
        }

    }

//    public String getFormattedCreatedDate() {
//        return createdDate.toLocalDate().toString();
//    }

}
