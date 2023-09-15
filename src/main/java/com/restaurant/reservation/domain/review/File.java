package com.restaurant.reservation.domain.review;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    private String originFileName;      //원본 파일명
    private String storeFileName;       //member가 업로드하는 파일명
    private String uploadDir;           //경로명
    private String extension;           //확장자
    private Long size;                  //파일 사이즈
    private String contentType;         //ContentType
    @CreatedDate
    private LocalDateTime regDate;

    @Builder
    public File(Long id, Review review, String originFileName, String storeFileName, String uploadDir, String extension, Long size, String contentType, LocalDateTime regDate) {
        this.id = id;
        this.review = review;
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.regDate = regDate;
    }

}
