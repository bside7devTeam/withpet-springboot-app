package org.gig.withpet.core.domain.community.communityImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/07/25
 */
@Repository
public interface CommunityAttachmentRepository extends JpaRepository<CommunityAttachment, Long> {
}
