package org.gig.withpet.core.domain.community.communityReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Repository
public interface CommunityReportRepository extends JpaRepository<CommunityReport, Long> {
}
