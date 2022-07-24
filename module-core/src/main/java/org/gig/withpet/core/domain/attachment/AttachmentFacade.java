package org.gig.withpet.core.domain.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.gig.withpet.core.domain.attachment.types.UsageType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : JAKE
 * @date : 2022/07/08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentFacade {

    private static final String formDataFileKey = "image";
    private static final String folderName = "image";

    private final ImageManagerService imageManagerService;
    private final AttachmentService attachmentService;

    public UploadFileDto.Response upload(MultipartFile multipartFile, UsageType usageType) throws Exception {

        String foldDlv = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String filePath = folderName + File.separator + foldDlv + "/profile";

        UploadFileDto uploadFileDto = imageManagerService.createAndUploadFile(multipartFile, filePath);
        attachmentService.create(uploadFileDto, usageType);

        return new UploadFileDto.Response(uploadFileDto.getFullPath());
    }
}
