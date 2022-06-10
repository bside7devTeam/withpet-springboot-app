package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.AnimalKindType;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author : JAKE
 * @date : 2022/06/03
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AdoptAnimal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopt_animal_id")
    private Long id;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'", nullable = false)
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'", nullable = false)
    @Enumerated(EnumType.STRING)
    private YnType neuterYn = YnType.N;

    private String noticeNo;

    private String desertionNo;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TerminalStatus terminalState;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private AnimalKindType animalKindType;

    private LocalDate noticeStartDate;

    private LocalDate noticeEndDate;

    private String noticeComment;

    private String careNm;

    private String careAddr;

    private String chargeNm;

    private String careTel;

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

    private LocalDate happenDate;

    private String happenPlace;

    public static AdoptAnimal insertPublicData(AnimalProtectDto dto, Long id, String upKindCd) {

        AdoptAnimal adoptAnimal = AdoptAnimal.builder()
                .id(id)
                .sex(dto.getSexCd()).kind(dto.getKindCd())
                .desertionNo(dto.getDesertionNo())
                .noticeNo(dto.getNoticeNo()).noticeComment(dto.getNoticeComment())
                .noticeStartDate(dto.getNoticeStartDate()).noticeEndDate(dto.getNoticeEndDate())
                .careAddr(dto.getCareAddr()).weight(dto.getWeight()).chargeNm(dto.getChargeNm())
                .careNm(dto.getCareNm()).careTel(dto.getCareTel())
                .happenPlace(dto.getHappenPlace()).happenDate(dto.getHappenDate())
                .officeTel(dto.getOfficetel()).orgNm(dto.getOrgNm())
                .filename(dto.getFilename()).popfile(dto.getPopfile())
                .neuterYn(dto.getNeuterYn().equals("Y") ? YnType.Y : YnType.N)
                .specialMark(dto.getSpecialMark()).color(dto.getColorCd()).age(dto.getAge())
                .build();

        adoptAnimal.setProcessStatus(dto);
        adoptAnimal.setTerminalStatus(dto);
        adoptAnimal.setAnimalKindType(upKindCd);

        return adoptAnimal;
    }

    public boolean isNotNeedUpdate(AnimalProtectDto dto, ProcessStatus processStatus) {

        if (!StringUtils.hasText(dto.getNoticeNo())) {
            return true;
        }

        return dto.getNoticeStartDate().equals(this.noticeStartDate)
                && dto.getNoticeEndDate().equals(this.noticeEndDate)
                && processStatus != null && processStatus == this.processStatus
                && dto.getCareAddr().equals(this.careAddr)
                && dto.getCareNm().equals(this.careNm)
                && dto.getCareTel().equals(this.careTel)
                && dto.getOfficetel().equals(this.officeTel)
                && dto.getSpecialMark().equals(this.specialMark);
    }

    private void setProcessStatus(AnimalProtectDto dto) {
        if (dto.getProcessState().equals("보호중")) {
            if (LocalDate.now().isAfter(dto.getNoticeEndDate())) {
                this.processStatus = ProcessStatus.PROTECT;
            } else {
                this.processStatus = ProcessStatus.NOTICE;
            }
        }

        if (dto.getProcessState().contains("종료")) {
            this.processStatus = ProcessStatus.TERMINAL;
        }

    }

    public ProcessStatus convertProcessStatus(AnimalProtectDto dto) {
        if (dto.getProcessState().equals("보호중")) {
            if (LocalDate.now().isAfter(dto.getNoticeEndDate())) {
                return ProcessStatus.PROTECT;
            } else {
                return ProcessStatus.NOTICE;
            }
        }

        if (dto.getProcessState().contains("종료")) {
            return ProcessStatus.TERMINAL;
        }

        return null;
    }

    private void setTerminalStatus(AnimalProtectDto dto) {

        if (dto.getProcessState().contains("종료")) {
            if (checkContainsYn("입양", dto.getProcessState())) {
                this.terminalState = TerminalStatus.ADOPT;
            }

            if (checkContainsYn("안락사", dto.getProcessState())) {
                this.terminalState = TerminalStatus.ENDOWMENT;
            }

            if (checkContainsYn("자연사", dto.getProcessState())) {
                this.terminalState = TerminalStatus.NATURAL_DEATH;
            }

            if (checkContainsYn("반환", dto.getProcessState())) {
                this.terminalState = TerminalStatus.RETURN;
            }

            if (checkContainsYn("방사", dto.getProcessState())) {
                this.terminalState = TerminalStatus.RADIATION;
            }

            if (checkContainsYn("기증", dto.getProcessState())) {
                this.terminalState = TerminalStatus.ENDOWMENT;
            }

            if (checkContainsYn("기타", dto.getProcessState())) {
                this.terminalState = TerminalStatus.ETC;
            }
        }

    }

    private void setAnimalKindType(String upKindCd) {

        if (!StringUtils.hasText(upKindCd)) {
            return;
        }

        switch (upKindCd) {
            case "417000" :
                this.animalKindType = AnimalKindType.PUPPY;
                break;
            case "422400" :
                this.animalKindType = AnimalKindType.CAT;
                break;
            case "429900" :
                this.animalKindType = AnimalKindType.ETC;
                break;
            default:
                this.animalKindType = null;
        }
    }

    private boolean checkContainsYn(String str, String target) {
        return target.contains(str);
    }
}
