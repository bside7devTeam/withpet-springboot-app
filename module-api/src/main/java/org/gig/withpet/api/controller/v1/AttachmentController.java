package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.attachment.AttachmentService;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : JAKE
 * @date : 2022/07/08
 */
@Slf4j
@RestController
@Api(value = "AttachmentController V1")
@RequestMapping("/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity<UploadFileDto> upload(
            @RequestParam(value = "file") MultipartFile multipartFile) throws IOException {

        UploadFileDto uploadFileDto = null;
        try {
            uploadFileDto = attachmentService.upload(multipartFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
        log.info("===== S3 multipartFileList Successfully Uploaded. =====");

        return ResponseEntity.ok().body(uploadFileDto);
    }
}
