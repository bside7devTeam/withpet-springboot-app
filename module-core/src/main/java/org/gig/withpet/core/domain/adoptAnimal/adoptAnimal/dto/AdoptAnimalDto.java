package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptAnimalDto {

    private Long adoptAnimalId;

    private String desertionNo;

    private String noticeNo;

    private String noticeSdt;

    private String noticeEdt;

    private ProcessStatus processStatus;

    private TerminalStatus terminalStatus;

    private String orgNm;

    private String popfile;

    private String specialMark;

    private String age;

    private String colorCd;

    private String kindCd;

    public AdoptAnimalDto(AdoptAnimal a) {
        this.adoptAnimalId = a.getId();
        this.desertionNo = a.getDesertionNo();
        this.noticeNo = a.getNoticeNo();
        this.noticeSdt = a.getNoticeSdt();
        this.noticeEdt = a.getNoticeEdt();
        this.processStatus = a.getProcessStatus();
        this.terminalStatus = a.getTerminalState();
        this.orgNm = a.getOrgNm();
        this.popfile = a.getPopfile();
        this.specialMark = a.getSpecialMark();
        this.age = a.getAge();
        this.colorCd = a.getColorCd();
        this.kindCd = a.getKindCd();
    }

}
