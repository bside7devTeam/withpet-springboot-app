package org.gig.withpet.core.domain.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gig.withpet.core.domain.attachment.types.FileType;

/**
 * @author : JAKE
 * @date : 2022/07/09
 */
@Getter
public class UploadFileDto {

    private FileType fileType;

    private String originalFileName;

    private String savedFilename;

    private String fullPath;

    private UploadFileDto(FileType fileType, String originalFileName, String savedFilename, String fullPath) {
        this.fileType = fileType;
        this.originalFileName = originalFileName;
        this.savedFilename = savedFilename;
        this.fullPath = fullPath;
    }


    public static UploadFileDto create(FileType fileType, String originalFileName, String savedFilename, String fullPath) {
        return new UploadFileDto(fileType, originalFileName, savedFilename, fullPath);
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String fullPath;
        private Long id;
    }
}
