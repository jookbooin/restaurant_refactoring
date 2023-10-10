package com.restaurant.reservation.domain.review;


import com.restaurant.reservation.repository.dto.UploadFileDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UploadFile {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
    private String originalFilename;      //원본 파일명
    private String storeFileName;       //member가 업로드하는 파일명
    private String uploadDir;           //경로명
    private String extension;           //확장자
    @CreatedDate
    private LocalDateTime uploadDate;      //등록 날짜

    @Builder
    public UploadFile(Long id, Review review, String originalFilename, String storeFileName, String uploadDir, String extension,  LocalDateTime uploadDate) {
        this.id = id;
        this.review = review;
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.uploadDate = uploadDate;
    }

    public static UploadFile fileFrom(UploadFileDto uploadFileDto){
        return UploadFile.builder()
                .originalFilename(uploadFileDto.getOriginalFilename())
                .storeFileName(uploadFileDto.getStoreFileName())
                .uploadDir(uploadFileDto.getUploadDir())
                .extension(uploadFileDto.getExtension())
                .build();
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
