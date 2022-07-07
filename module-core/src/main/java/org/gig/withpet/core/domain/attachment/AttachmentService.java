package org.gig.withpet.core.domain.attachment;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.gig.withpet.core.utils.AmazonS3Utils;
import org.gig.withpet.core.utils.properties.S3Properties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * @author : JAKE
 * @date : 2022/07/08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final S3Properties s3Properties;

    public String upload(MultipartFile multipartFile) throws IOException {

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndPoint(), s3Properties.getRegionName()))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
                .build();


        String bucketName = s3Properties.getBucketName();

        String folderName = "profile-image/";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0L);
        objectMetadata.setContentType("application/x-directory");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, new ByteArrayInputStream(new byte[0]), objectMetadata);

        try {
            s3.putObject(putObjectRequest);
            System.out.format("Folder %s has been created.\n", folderName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }


        // upload local file
        String objectName = "jake";
        String filePath = multipartFile.getOriginalFilename();
        validFileUpload(filePath);

        try {
            s3.putObject(bucketName, objectName, new File(filePath));
            System.out.format("Object %s has been created.\n", objectName);
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void validFileUpload(String originalFileName) {

        boolean isPermit = FileUtils.permitExtensionCheck(originalFileName);

        if (!isPermit) {
            throw new InvalidParameterException("해당 파일은 업로드하실 수 없습니다.<br />확인 후 다시 업로드해주시기 바랍니다.");
        }
    }
}
