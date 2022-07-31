package org.gig.withpet.core.domain.community.communityReport;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.community.community.dto.CommunityDto;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Service
@RequiredArgsConstructor
public class CommunityReportService {

    private final CommunityReportRepository communityReportRepository;

    @Transactional
    public void report(CommunityDto.ReportRequest request, Community community, Member loginUser) {

        CommunityReport communityReport = CommunityReport
                .create(request.getReportType(), request.getReportReason(), community, loginUser);

        communityReportRepository.save(communityReport);
    }
}
