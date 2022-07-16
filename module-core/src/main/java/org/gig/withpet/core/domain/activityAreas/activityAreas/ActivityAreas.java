package org.gig.withpet.core.domain.activityAreas.activityAreas;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.activityAreas.activityEmdAreaas.ActivityEmdAreas;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "activityAreas", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ActivityEmdAreas> activityEmdAreas = new ArrayList<>();

    public static ActivityAreas insertActivityAreas(Member member) {
        return ActivityAreas.builder()
                .member(member)
                .build();
    }
}
