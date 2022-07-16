package org.gig.withpet.core.domain.shelter;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectShelterDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.shelter.dto.ShelterResDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String tel;

    private String landAddress;

    private String roadAddress;

    private String rescueAnimal;

    private String beginTime;

    private String endTime;

    private String holiday;

    private String institutionName;

    private String institutionCode;

    private String adoptAnimalRegNo;

    private String coordinateX;

    private String coordinateY;

    private String sidoCode;

    private String siggCode;

    @Builder.Default
    @OneToMany(mappedBy = "shelter", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<AdoptAnimal> adoptAnimals = new ArrayList<>();

    public static Shelter insertPublicData(AnimalProtectShelterDto dto, String sidoCode, String siggCode) {
        return Shelter.builder()
                .name(dto.getName())
                .adoptAnimalRegNo(dto.getRegNo())
                .sidoCode(sidoCode)
                .siggCode(siggCode)
                .build();
    }

    public static Shelter insertShelterApi(ShelterResDto dto) {
        return Shelter.builder()
                .name(dto.getAnimalCnterNm())
                .institutionCode(dto.getInsttCode())
                .institutionName(dto.getInstitutionNm())
                .landAddress(dto.getLnmadr())
                .roadAddress(dto.getRdnmadr())
                .coordinateX(dto.getLatitude())
                .coordinateY(dto.getLongitude())
                .tel(dto.getPhoneNumber())
                .rescueAnimal(dto.getRescueAnimal())
                .beginTime(dto.getWeekdayLttotBeginHhmm())
                .endTime(dto.getWeekdayLttotEndHhmm())
                .holiday(dto.getRstde())
                .build();
    }
}
