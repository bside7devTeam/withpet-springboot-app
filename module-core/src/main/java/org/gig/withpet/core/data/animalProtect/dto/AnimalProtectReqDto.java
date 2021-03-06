package org.gig.withpet.core.data.animalProtect.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2022/05/20
 */
@Getter
@Setter
@Builder
public class AnimalProtectReqDto {

    private String upkind;
    private String state;
    private String bgnde;
    private String endde;
    private String neuterYn;
    private String careRegNo;
    private String uprCd;
    private String orgCd;

    private String saveYn;
    private String mappingYn;

    @Builder.Default
    private Integer pageNo = 1;

    @Builder.Default
    private Integer numOfRows = 10;
}
