package org.gig.withpet.core.domain.community.commentAttachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Repository
public interface CommunityCommentAttachmentRepository extends JpaRepository<CommunityCommentAttachment, Long> {
}
