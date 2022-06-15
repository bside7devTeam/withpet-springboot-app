package org.gig.withpet.core.data.animalShelter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2022/06/15
 */
@Getter
@Setter
@Builder
public class AnimalShelterReqDto {

    private String type;

    private String saveYn;

    @Builder.Default
    private Integer pageNo = 1;

    @Builder.Default
    private Integer numOfRows = 10;

}
