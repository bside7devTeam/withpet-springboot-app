package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.attachment.AttachmentFacade;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.gig.withpet.core.domain.attachment.types.UsageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final AttachmentFacade attachmentService;

    @PostMapping("upload")
    @ResponseBody
    public ResponseEntity<ApiResponse> upload(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "usageType") UsageType usageType) throws IOException {

        UploadFileDto.Response result = null;
        try {
            result = attachmentService.upload(multipartFile, usageType);
        } catch(Exception e) {
            e.printStackTrace();
        }
        log.info("===== S3 multipartFileList Successfully Uploaded. =====");

        return new ResponseEntity<>(ApiResponse.OK(result), HttpStatus.OK);
    }
}
