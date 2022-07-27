package org.gig.withpet.core.domain.community.commentAttachment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Service
@RequiredArgsConstructor
public class CommunityCommentAttachmentService {

    private final CommunityCommentAttachmentRepository commentAttachmentRepository;

    public void create(CommunityCommentAttachment commentAttachment) {
        commentAttachmentRepository.save(commentAttachment);
    }
}
