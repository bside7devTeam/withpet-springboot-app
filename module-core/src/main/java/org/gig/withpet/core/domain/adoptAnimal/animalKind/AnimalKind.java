package org.gig.withpet.core.domain.adoptAnimal.animalKind;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectKindDto;
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
public class AnimalKind extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_kind_id")
    private Long id;

    private String kindCd;

    private String KNm;

    private String upKindCd;

    public static AnimalKind insertPublicData(AnimalProtectKindDto dto, String upKindCd) {
        return AnimalKind.builder()
                .kindCd(dto.getKindCd())
                .KNm(dto.getKNm())
                .upKindCd(upKindCd)
                .build();
    }
}
