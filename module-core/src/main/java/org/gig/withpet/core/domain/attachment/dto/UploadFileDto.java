package org.gig.withpet.core.domain.attachment.dto;

import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2022/07/09
 */
@Getter
public class UploadFileDto {

    private String originalFileName;

    private String uploadFilePath;

    private UploadFileDto(String originalFileName, String uploadFilePath) {
        this.originalFileName = originalFileName;
        this.uploadFilePath = uploadFilePath;
    }

    public static UploadFileDto create(String originalFileName, String uploadFilePath) {
        return new UploadFileDto(originalFileName, uploadFilePath);
    }
}
