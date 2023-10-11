package com.restaurant.reservation.api.response;

import com.restaurant.reservation.repository.dto.UploadFileDto;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResponse {
    private String uploadDir;
    private String storeFileName;

    @Builder
    public ImageResponse(String uploadDir, String storeFileName) {
        this.uploadDir = uploadDir;
        this.storeFileName = storeFileName;
    }

    public static ImageResponse responseFrom(UploadFileDto dto){
       return ImageResponse.builder()
                .storeFileName(dto.getStoreFileName())
                .uploadDir(dto.getUploadDir())
                .build();
    }
}
