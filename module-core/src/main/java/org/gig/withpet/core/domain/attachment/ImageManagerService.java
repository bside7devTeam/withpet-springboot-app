package org.gig.withpet.core.domain.attachment;

import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/09
 */
@Service
@RequiredArgsConstructor
public class ImageManagerService {

    private final FileManager fileManager;
    private final UploadImageS3 uploadImage;

    public UploadFileDto createAndUploadFile(MultipartFile mf, String filePath) throws Exception {

        uploadImage.createBucketFolder("image/");

        long time = System.currentTimeMillis();
        String originalFilename = mf.getOriginalFilename();
        String saveFileName = String.format("%d_%s", time, originalFilename.replaceAll(" ", ""));

        File uploadFile = null;
        try {
            Optional<File> uploadFileOpt = fileManager.convertMultipartFileToFile(mf);
            if (uploadFileOpt.isEmpty()) {
                throw new Exception("파일변환에 실패했습니다.");
            }
            uploadFile = uploadFileOpt.get();

            String saveFilePath = uploadImage.upload(uploadFile, filePath, saveFileName);

            return UploadFileDto.create(originalFilename, File.separator + saveFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("파일을 업로드 하던 중 에러가 발생했습니다.");
        }  finally {
            if (uploadFile != null) {
                uploadFile.delete();
            }
        }
    }

}
