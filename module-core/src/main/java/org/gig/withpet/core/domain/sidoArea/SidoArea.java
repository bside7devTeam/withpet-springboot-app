package org.gig.withpet.core.domain.sidoArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.AnimalProtectKindDto;
import org.gig.withpet.core.data.animalProtect.AnimalProtectSidoDto;
import org.gig.withpet.core.domain.adoptAnimal.AnimalKind;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;

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
    @Column(name = "sido_area_id")
    private Long id;

    private String admCode;

    private String admName;

    private String version;

    public static SidoArea insertPublicData(AnimalProtectSidoDto dto) {
        return SidoArea.builder()
                .admCode(dto.getAdmCode())
                .admName(dto.getAdmName())
                .build();
    }

}
