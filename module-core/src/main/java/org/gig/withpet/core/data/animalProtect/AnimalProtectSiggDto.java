package org.gig.withpet.core.data.animalProtect;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Getter
@NoArgsConstructor
public class AnimalProtectSiggDto {

    @JsonProperty(value = "orgCd")
    private String admCode;

    @JsonProperty(value = "orgdownNm")
    private String admName;

    @JsonProperty(value = "uprCd")
    private String parentAdmCode;
}
