package org.gig.withpet.core.data.animalProtect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/06/05
 */
@Getter
@NoArgsConstructor
public class AnimalProtectShelterDto {

    @JsonProperty(value = "careNm")
    private String name;

    @JsonProperty(value = "careRegNo")
    private String regNo;

}
