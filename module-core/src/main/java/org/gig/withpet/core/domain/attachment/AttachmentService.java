package org.gig.withpet.core.domain.attachment;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.gig.withpet.core.domain.attachment.types.UsageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2022/07/24
 */
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Transactional
    public void create(UploadFileDto uploadFileDto, UsageType usageType) {

        Attachment attachment = Attachment.Of(
                usageType,
                uploadFileDto.getFileType(), uploadFileDto.getOriginalFileName(),
                uploadFileDto.getSavedFilename(), uploadFileDto.getFullPath());

        attachmentRepository.save(attachment);
    }
}
