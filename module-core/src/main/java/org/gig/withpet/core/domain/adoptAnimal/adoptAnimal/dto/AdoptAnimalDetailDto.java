package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.common.types.YnType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2022/07/02
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptAnimalDetailDto extends AdoptAnimalDto {

    private static final AdoptAnimalDetailDto EMPTY;

    private String careNm;

    private String careTel;

    private LocalDate happenDate;

    private String happenPlace;

    private YnType neuterYn;

    private String chargeNm;

    private String officeTel;

    static {
        EMPTY = AdoptAnimalDetailDto.builder()
                .empty(true)
                .build();
    }

    @Builder.Default
    private boolean empty = false;

    public static AdoptAnimalDetailDto emptyDto() {
        return EMPTY;
    }

    public AdoptAnimalDetailDto(AdoptAnimal a) {
        super(a);
        this.careNm = a.getCareNm();
        this.careTel = a.getCareTel();
        this.happenDate = a.getHappenDate();
        this.happenPlace = a.getHappenPlace();
        this.neuterYn = a.getNeuterYn();
        this.chargeNm = a.getChargeNm();
        this.officeTel = a.getOfficeTel();
    }

}
