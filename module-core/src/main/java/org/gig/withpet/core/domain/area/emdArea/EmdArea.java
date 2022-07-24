package org.gig.withpet.core.domain.area.emdArea;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.domain.activityAreas.ActivityAreas;
import org.gig.withpet.core.domain.area.siggArea.SiggArea;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmdArea extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emd_id")
    private Long id;

    private String admCode;

    private String admName;

    private String version;

    @Column(length = 1000)
    private String geometry;

    private String coordinateX;

    private String coordinateY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigg_id")
    private SiggArea sigg;

//    @Builder.Default
//    @OneToMany(mappedBy = "activityAreas", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
//    private List<ActivityAreas> activityAreas = new ArrayList<>();

    public static EmdArea insertPublicData(AddressResDto address, String admName) {
        return EmdArea
                .builder()
                .admCode(address.getId())
                .admName(admName)
                .geometry(address.getGeometry())
                .coordinateX(address.getPoint().getX())
                .coordinateY(address.getPoint().getY())
                .build();
    }

    public void addParent(SiggArea siggArea) {
        this.sigg = siggArea;
    }
}
