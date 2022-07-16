package org.gig.withpet.core.domain.shelter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

/**
 * @author : JAKE
 * @date : 2022/07/02
 */
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShelterResDto {

    private String animalCnterNm;

    private String lnmadr;

    private String rdnmadr;

    private String latitude;

    private String longitude;

    private String phoneNumber;

    private String rescueAnimal;

    private String weekdayLttotBeginHhmm;

    private String weekdayLttotEndHhmm;

    private String rstde;

    private String institutionNm;

    private String insttCode;

}
