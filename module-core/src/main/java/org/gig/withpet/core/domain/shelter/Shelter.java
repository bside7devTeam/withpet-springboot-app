package org.gig.withpet.core.domain.shelter;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectShelterDto;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;

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

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'", nullable = false)
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

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
