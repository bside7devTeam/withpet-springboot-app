package org.gig.withpet.core.domain.community.comment;

import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    Optional<CommunityComment> findByIdAndDeleteYn(Long id, YnType deleteYn);
}
