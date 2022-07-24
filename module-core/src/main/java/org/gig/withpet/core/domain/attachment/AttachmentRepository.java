package org.gig.withpet.core.domain.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/07/24
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
