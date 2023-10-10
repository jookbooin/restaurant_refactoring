package com.restaurant.reservation.repository.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFileDto {
    private Long id;                    //id

    private String originalFilename;      //원본 파일명

    private String storeFileName;       //저장된 파일명

    private String uploadDir;           //경로명

    private String extension;           //확장자


    private Long size;                  //파일 사이즈

    private String contentType;         //ContentType
    private LocalDateTime uploadDate;

    @Builder
    public UploadFileDto(Long id, String originalFilename, String storeFileName, String uploadDir, String extension, Long size, String contentType, LocalDateTime uploadDate) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
        this.uploadDate = uploadDate;
    }






}
