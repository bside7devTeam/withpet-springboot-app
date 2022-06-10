package org.gig.withpet.core.domain.shelter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectShelterDto;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/06/05
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shelter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelter_id")
    private Long id;

    private String name;

    private String regNo;

    private String sidoCode;

    private String siggCode;

    public static Shelter insertPublicData(AnimalProtectShelterDto dto, String sidoCode, String siggCode) {
        return Shelter.builder()
                .name(dto.getName())
                .regNo(dto.getRegNo())
                .sidoCode(sidoCode)
                .siggCode(siggCode)
                .build();
    }
}
