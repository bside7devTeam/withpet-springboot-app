package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.AnimalKindType;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.vo.AdoptAnimalVo;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.shelter.Shelter;
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
    @Column(columnDefinition = "varchar(2) default 'N'")
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

    private LocalDate adoptSuccessDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public static AdoptAnimal insertPublicData(AdoptAnimalVo vo, Long id) {

        return AdoptAnimal.builder()
                .id(id)
                .sex(vo.getSex()).kind(vo.getKind())
                .desertionNo(vo.getDesertionNo())
                .noticeNo(vo.getNoticeNo()).noticeComment(vo.getNoticeComment())
                .adoptSuccessDate(vo.getAdoptSuccessDate())
                .noticeStartDate(vo.getNoticeStartDate()).noticeEndDate(vo.getNoticeEndDate())
                .careAddr(vo.getCareAddr()).weight(vo.getWeight()).chargeNm(vo.getChargeNm())
                .careNm(vo.getCareNm()).careTel(vo.getCareTel())
                .happenPlace(vo.getHappenPlace()).happenDate(vo.getHappenDate())
                .officeTel(vo.getOfficeTel()).orgNm(vo.getOrgNm())
                .filename(vo.getFilename()).popfile(vo.getPopfile())
                .neuterYn(vo.getNeuterYn())
                .specialMark(vo.getSpecialMark()).color(vo.getColor()).age(vo.getAge())
                .processStatus(vo.getProcessStatus()).terminalState(vo.getTerminalState()).animalKindType(vo.getAnimalKindType())
                .build();
    }

    public boolean isNotNeedUpdate(AdoptAnimalVo vo) {

        if (!StringUtils.hasText(vo.getNoticeNo())) {
            return true;
        }

        return vo.getNoticeStartDate().equals(this.noticeStartDate)
                && vo.getNoticeEndDate().equals(this.noticeEndDate)
                && vo.getProcessStatus() == this.processStatus
                && vo.getCareAddr().equals(this.careAddr)
                && vo.getCareNm().equals(this.careNm)
                && vo.getCareTel().equals(this.careTel)
                && vo.getOfficeTel().equals(this.officeTel)
                && vo.getSpecialMark().equals(this.specialMark);
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}
