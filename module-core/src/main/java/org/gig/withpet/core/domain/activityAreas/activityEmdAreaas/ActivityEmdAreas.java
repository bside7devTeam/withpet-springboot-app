package org.gig.withpet.core.domain.activityAreas.activityEmdAreaas;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.activityAreas.activityAreas.ActivityAreas;
import org.gig.withpet.core.domain.area.emdArea.EmdArea;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/07/10
 */
@Entity
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityEmdAreas extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_emd_area_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_areas_id")
    private ActivityAreas activityAreas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emd_id")
    private EmdArea emdArea;

    public static ActivityEmdAreas addActivityEmdAreas(ActivityAreas activityAreas, EmdArea emdArea) {
        return ActivityEmdAreas.builder()
                .activityAreas(activityAreas)
                .emdArea(emdArea)
                .build();
    }
}
