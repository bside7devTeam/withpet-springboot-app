package org.gig.withpet.core.domain.community.communityReport;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.community.community.types.ReportType;
import org.gig.withpet.core.domain.user.member.Member;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_like_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String reportReason;

    public static CommunityReport create(ReportType reportType, String reportReason, Community community, Member member) {
        return CommunityReport.builder()
                .reportType(reportType)
                .reportReason(reportReason)
                .community(community)
                .member(member)
                .build();
    }
}
