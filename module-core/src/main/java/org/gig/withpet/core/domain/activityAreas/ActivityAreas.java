package org.gig.withpet.core.domain.activityAreas;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.area.emdArea.EmdArea;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2022/07/10
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ActivityAreas extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_areas_id")
    private Long id;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

    private LocalDateTime authenticatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emd_area_id")
    private EmdArea emdArea;

    public static ActivityAreas insertActivityAreas(Member member, EmdArea emdArea) {
        return ActivityAreas.builder()
                .member(member)
                .emdArea(emdArea)
                .build();
    }
}
