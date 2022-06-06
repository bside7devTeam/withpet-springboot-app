package org.gig.withpet.core.data.animalProtect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Getter
@NoArgsConstructor
public class AnimalProtectKindDto {

    @JsonProperty(value = "kindCd")
    private String kindCd;

    @JsonProperty(value = "KNm")
    private String kNm;
}
