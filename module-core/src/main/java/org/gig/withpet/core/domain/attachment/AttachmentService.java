package org.gig.withpet.core.domain.attachment;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.attachment.dto.UploadFileDto;
import org.gig.withpet.core.domain.attachment.types.UsageType;
import org.gig.withpet.core.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/24
 */
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Transactional
    public Long create(UploadFileDto uploadFileDto, UsageType usageType) {

        Attachment attachment = Attachment.Of(
                usageType,
                uploadFileDto.getFileType(), uploadFileDto.getOriginalFileName(),
                uploadFileDto.getSavedFilename(), uploadFileDto.getFullPath());

        return attachmentRepository.save(attachment).getId();
    }

    @Transactional(readOnly = true)
    public Attachment findById(Long id) {
        Optional<Attachment> findAttachment = attachmentRepository.findById(id);
        if (findAttachment.isEmpty()) {
            throw new NotFoundException("해당 파일 정보를 찾을 수 없습니다.");
        }
        return findAttachment.get();
    }
}
