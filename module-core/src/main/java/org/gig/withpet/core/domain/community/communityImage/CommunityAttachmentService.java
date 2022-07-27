package org.gig.withpet.core.domain.community.communityImage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2022/07/25
 */
@Service
@RequiredArgsConstructor
public class CommunityAttachmentService {

    private final CommunityAttachmentRepository repository;

    @Transactional
    public void create(CommunityAttachment communityAttachment) {
        repository.save(communityAttachment);
    }
}
