package com.restaurant.reservation.service;

import com.restaurant.reservation.common.exception.domain.FileException;
import com.restaurant.reservation.domain.review.UploadFile;
import com.restaurant.reservation.repository.dto.UploadFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    @Value("${upload.path}")
    private String uploadDir;

    public String getFullPath(String filename) {
        return uploadDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFileList)throws IOException {
        List<UploadFile> storeFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (!multipartFile.isEmpty()) {
                storeFileList.add(storeFile(multipartFile));
            }
        }
        return storeFileList;
    }
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();

        UploadFileDto uploadFileDto = UploadFileDto.builder()
                .originalFilename(originalFilename)
                .storeFileName(createStoreFileName(originalFilename))
                .uploadDir(this.uploadDir)
                .extension(extractExtension(originalFilename))
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .build();

        multipartFile.transferTo(new File(getFullPath(uploadFileDto.getStoreFileName())));

        return UploadFile.fileFrom(uploadFileDto);
    }
    private String createStoreFileName(String originalFilename) {
        String extension = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }
    private String extractExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


    public List<MultipartFile> imageContentType(List<MultipartFile> multipartFileList){
        List<MultipartFile> imageFileList = new ArrayList<>();

            for (MultipartFile multipartFile : multipartFileList) {
                if (!multipartFile.isEmpty()) {
                    String contentType = multipartFile.getContentType();
                    log.info("contentType : {}", contentType);
                    if (contentType.equals("image/jpeg") || contentType.equals("image/png")) {
                        imageFileList.add(multipartFile);
                    } else
                        throw new FileException("이미지 파일만 저장할 수 있습니다.");
                }
            }

        return imageFileList;
    }
}
