package com.restaurant.reservation.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.restaurant.reservation.domain.review.Review;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchDto {
    private Long id;
    private Long restaurantId;
    private String content; // 후기
    private int grade;   // 평점
    private LocalDateTime createdDate;
    private Long memberId; // Member
    private String name;  // Member

    private List<UploadFileDto> uploadFileDtoList = new ArrayList<>();

    @QueryProjection
    public ReviewSearchDto(Long id,String content, int grade, LocalDateTime createdDate, Long restaurantId,Long memberId, String name) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.createdDate = createdDate;
        this.restaurantId = restaurantId;
        this.memberId = memberId;
        this.name = name;
    }

    public ReviewSearchDto(Review review) {
        this.id = review.getId();
        this.restaurantId =review.getRestaurant().getId();
        this.content = review.getContent();
        this.grade = review.getGrade();
        this.createdDate = review.getCreatedDate();
        this.memberId = review.getMember().getId();
        this.name = review.getMember().getMemberInfo().getName();

        if(!review.getUploadFileList().isEmpty())
            this.uploadFileDtoList = review.getUploadFileList().stream()
                .map(uploadFile ->UploadFileDto.dtoFrom(uploadFile))
                .collect(Collectors.toList());
    }

}
