package org.gig.withpet.core.domain.Area;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectSidoDto;
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

    @Builder.Default
    @OneToMany(mappedBy = "sido", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SiggArea> siggAreas = new ArrayList<>();

    public static SidoArea insertPublicData(AnimalProtectSidoDto dto) {
        return SidoArea.builder()
                .admCode(dto.getAdmCode())
                .admName(dto.getAdmName())
                .build();
    }

}
