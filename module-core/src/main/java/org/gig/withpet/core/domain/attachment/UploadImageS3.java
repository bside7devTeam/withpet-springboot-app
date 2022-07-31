package org.gig.withpet.core.domain.attachment;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.utils.properties.S3Properties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author : JAKE
 * @date : 2022/07/09
 */
@Component
@RequiredArgsConstructor
public class UploadImageS3 {

    private final S3Properties s3Properties;

    public String upload(File uploadFile, String filePath, String saveFileName) {

        AmazonS3 s3Client = getS3Client();
        String fileName = filePath + File.separator + saveFileName;
        s3Client.putObject(new PutObjectRequest(s3Properties.getBucketName(), fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Properties.getPrefixUrl() + s3Properties.getBucketName() + File.separator + fileName;
    }

    public void createBucketFolder(String folderName) {

        AmazonS3 s3Client = getS3Client();
        String bucketName = s3Properties.getBucketName();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0L);
        objectMetadata.setContentType("application/x-directory");

        s3Client.putObject(new PutObjectRequest(bucketName, folderName, new ByteArrayInputStream(new byte[0]), objectMetadata));
        System.out.format("Folder %s has been created.\n", folderName);
    }

    private AmazonS3 getS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndPoint(), s3Properties.getRegionName()))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
                .build();
    }
}
