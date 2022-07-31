package org.gig.withpet.core.domain.community.commentAttachment;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.comment.CommunityComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Service
@RequiredArgsConstructor
public class CommunityCommentAttachmentService {

    private final CommunityCommentAttachmentRepository attachmentRepository;

    @Transactional
    public void create(CommunityCommentAttachment commentAttachment) {
        attachmentRepository.save(commentAttachment);
    }

    @Transactional
    public void deleteAttachmentsByComment(CommunityComment comment) {
        List<CommunityCommentAttachment> attachments
                = attachmentRepository.findCommunityCommentAttachmentsByCommentAndDeleteYn(comment, YnType.N);

        for (CommunityCommentAttachment attachment : attachments) {
            attachment.delete();
        }

        attachmentRepository.saveAll(attachments);
    }

    @Transactional(readOnly = true)
    public List<CommunityCommentAttachment> getAttachmentByComment(CommunityComment comment) {
        return attachmentRepository.findCommunityCommentAttachmentsByCommentAndDeleteYn(comment, YnType.N);
    }
}
