package org.gig.withpet.core.domain.attachment;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.gig.withpet.core.utils.properties.S3Properties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private static final String formDataFileKey = "image";
    private static final String folderName = "image";

    private final ImageManagerService imageManagerService;

    public UploadFileDto upload(MultipartFile multipartFile) throws Exception {

        String foldDlv = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String filePath = folderName + File.separator + foldDlv + "/profile";

        return imageManagerService.createAndUploadFile(multipartFile, filePath);
    }
}
