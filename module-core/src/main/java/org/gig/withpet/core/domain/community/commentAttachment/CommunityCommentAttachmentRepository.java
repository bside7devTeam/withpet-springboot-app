package org.gig.withpet.core.domain.community.commentAttachment;

import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.comment.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Repository
public interface CommunityCommentAttachmentRepository extends JpaRepository<CommunityCommentAttachment, Long> {

    List<CommunityCommentAttachment> findCommunityCommentAttachmentsByCommentAndDeleteYn(CommunityComment comment, YnType deleteYn);
}
