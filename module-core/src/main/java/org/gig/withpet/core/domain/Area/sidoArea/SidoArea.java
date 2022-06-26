package org.gig.withpet.core.domain.Area.sidoArea;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectSidoDto;
import org.gig.withpet.core.data.vWorldAddress.dto.AddressResDto;
import org.gig.withpet.core.domain.Area.siggArea.SiggArea;
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
public class SidoArea extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sido_id")
    private Long id;

    private String admCode;

    private String admName;

    private String version;

    private String adoptAnimalAdmCode;

    @Column(length = 1000)
    private String geometry;

    private String coordinateX;

    private String coordinateY;

    @Builder.Default
    @OneToMany(mappedBy = "sido", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SiggArea> siggAreas = new ArrayList<>();

    public static SidoArea insertPublicData(AnimalProtectSidoDto dto) {
        return SidoArea.builder()
                .admCode(dto.getAdmCode())
                .admName(dto.getAdmName())
                .build();
    }

    public static SidoArea insertVWorldData(AddressResDto dto) {
        return SidoArea.builder()
                .admCode(dto.getId())
                .admName(dto.getTitle())
                .geometry(dto.getGeometry())
                .coordinateX(dto.getPoint().getX())
                .coordinateY(dto.getPoint().getY())
                .build();
    }

    public void setAdoptAnimalAdmCode(String admCode) {
        this.adoptAnimalAdmCode = admCode;
    }

}
