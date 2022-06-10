package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDate noticeStartDate;

    private LocalDate noticeEndDate;

    private ProcessStatus processStatus;

    private TerminalStatus terminalStatus;

    private String orgNm;

    private String popfile;

    private String specialMark;

    private String age;

    private String color;

    private String kind;

    public AdoptAnimalDto(AdoptAnimal a) {
        this.adoptAnimalId = a.getId();
        this.desertionNo = a.getDesertionNo();
        this.noticeNo = a.getNoticeNo();
        this.noticeStartDate = a.getNoticeStartDate();
        this.noticeEndDate = a.getNoticeEndDate();
        this.processStatus = a.getProcessStatus();
        this.terminalStatus = a.getTerminalState();
        this.orgNm = a.getOrgNm();
        this.popfile = a.getPopfile();
        this.specialMark = a.getSpecialMark();
        this.age = a.getAge();
        this.color = a.getColor();
        this.kind = a.getKind();
    }

}
