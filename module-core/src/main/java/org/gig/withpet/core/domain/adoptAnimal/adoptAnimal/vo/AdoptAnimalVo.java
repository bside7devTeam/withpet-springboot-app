package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.AnimalKindType;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.domain.common.types.YnType;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author : JAKE
 * @date : 2022/06/11
 */
@Getter
@AllArgsConstructor
public class AdoptAnimalVo {

    private Long id;

    private YnType neuterYn;

    private String noticeNo;

    private String desertionNo;

    private LocalDate noticeStartDate;

    private LocalDate noticeEndDate;

    private ProcessStatus processStatus;

    private TerminalStatus terminalState;

    private AnimalKindType animalKindType;

    private String noticeComment;

    private String careNm;

    private String careAddr;

    private String careTel;

    private String chargeNm;

    private String officeTel;

    private String orgNm;

    private String sex;

    private String color;

    private String age;

    private String filename;

    private String popfile;

    private String kind;

    private String weight;

    private String specialMark;

    private String happenPlace;

    private LocalDate happenDate;

    public AdoptAnimalVo(AnimalProtectDto dto, String upKindCd) {
        this.noticeNo = dto.getNoticeNo();
        this.noticeComment = dto.getNoticeComment();
        this.desertionNo = dto.getDesertionNo();
        this.noticeStartDate = dto.getNoticeStartDate();
        this.noticeEndDate = dto.getNoticeEndDate();
        this.neuterYn = YnType.findByCode(dto.getNeuterYn());

        this.careNm = dto.getCareNm();
        this.careAddr = dto.getCareAddr();
        this.careTel = dto.getCareTel();
        this.chargeNm = dto.getChargeNm();
        this.officeTel = dto.getOfficetel();
        this.orgNm = dto.getOrgNm();
        this.sex = dto.getSexCd();
        this.color = dto.getColorCd();
        this.age = dto.getAge();
        this.filename = dto.getFilename();
        this.popfile = dto.getPopfile();
        this.kind = dto.getKindCd();
        this.weight = dto.getWeight();
        this.specialMark = dto.getSpecialMark();
        this.happenPlace = dto.getHappenPlace();
        this.happenDate = dto.getHappenDate();

        this.processStatus = ProcessStatus.findByCodeAndNoticeEndDate(dto.getProcessState(), this.noticeEndDate);
        this.terminalState = TerminalStatus.findByTerminalState(dto.getProcessState());
        this.animalKindType = AnimalKindType.findByUpKindCd(upKindCd);
    }
}
