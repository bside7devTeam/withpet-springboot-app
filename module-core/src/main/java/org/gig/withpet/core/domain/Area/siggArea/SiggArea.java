package org.gig.withpet.core.domain.Area.siggArea;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectSiggDto;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.domain.Area.emdArea.EmdArea;
import org.gig.withpet.core.domain.Area.sidoArea.SidoArea;

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
public class SiggArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sigg_id")
    private Long id;

    private String admCode;

    private String admName;

    private String version;

    @Column(length = 1000)
    private String geometry;

    private String coordinateX;

    private String coordinateY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_id")
    private SidoArea sido;

    @Builder.Default
    @OneToMany(mappedBy = "sigg", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<EmdArea> emdAreas = new ArrayList<>();

    public static SiggArea insertPublicData(AnimalProtectSiggDto dto) {
        return SiggArea.builder()
                .admCode(dto.getAdmCode())
                .admName(dto.getAdmName())
                .build();
    }

    public static SiggArea insertVWorldData(AddressResDto dto, String admName) {
        return SiggArea.builder()
                .admCode(dto.getId())
                .admName(admName)
                .geometry(dto.getGeometry())
                .coordinateX(dto.getPoint().getX())
                .coordinateY(dto.getPoint().getY())
                .build();
    }

    public void addParent(SidoArea sidoArea) {
        this.sido = sidoArea;
    }
}
