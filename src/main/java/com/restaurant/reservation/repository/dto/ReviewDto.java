package com.restaurant.reservation.repository.dto;

import com.restaurant.reservation.web.form.ReviewSaveForm;
import com.restaurant.reservation.web.form.ReviewUpdateForm;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    private List<MultipartFile> multipartFileList = new ArrayList<>();


    @Builder
    public ReviewDto(Long id, String content, Long memberId, Long restaurantId, int grade, int viewCount, List<MultipartFile> multipartFileList) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.restaurantId = restaurantId;
        this.grade = grade;
        this.viewCount = viewCount;
        this.multipartFileList = multipartFileList;
    }


    public void setMultipartFileList(List<MultipartFile> multipartFileList) {
        this.multipartFileList = multipartFileList;
    }

    public static ReviewDto saveOf(Long rtid, Long mid, ReviewSaveForm form){
        return ReviewDto.builder()
                .grade(form.getGrade())
                .content(form.getContent())
                .memberId(mid)
                .restaurantId(rtid)
                .multipartFileList(form.getMultipartFileList())
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



}
