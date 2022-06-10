package org.gig.withpet.core.data.animalProtect.adoptAnimalData;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectDto;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.util.StringUtils;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/06/08
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AdoptAnimalData extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopt_animal_data_id")
    private Long id;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'", nullable = false)
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

    private String desertionNo;

    private String noticeNo;

    private String noticeSdt;

    private String noticeEdt;

    private String processState;

    private String noticeComment;

    private String careNm;

    private String careAddr;

    private String chargeNm;

    private String careTel;

    private String officeTel;

    private String orgNm;

    private String sexCd;

    private String kindCd;

    private String weight;

    private String happenDt;

    private String happenPlace;

    private String filename;

    private String popfile;

    private String neuterYn;

    private String specialMark;

    private String colorCd;

    private String age;

    public static AdoptAnimalData insertPublicData(AnimalProtectDto dto, Long id) {
        return AdoptAnimalData.builder()
                .id(id)
                .sexCd(dto.getSexCd()).kindCd(dto.getKindCd()).noticeNo(dto.getNoticeNo())
                .careAddr(dto.getCareAddr()).processState(dto.getProcessState()).noticeSdt(dto.getNoticeSdt())
                .noticeEdt(dto.getNoticeEdt()).weight(dto.getWeight()).chargeNm(dto.getChargeNm())
                .careNm(dto.getCareNm()).desertionNo(dto.getDesertionNo()).careTel(dto.getCareTel())
                .happenPlace(dto.getHappenPlace()).officeTel(dto.getOfficetel()).orgNm(dto.getOrgNm())
                .filename(dto.getFilename()).popfile(dto.getPopfile()).neuterYn(dto.getNeuterYn())
                .specialMark(dto.getSpecialMark()).colorCd(dto.getColorCd()).happenDt(dto.getHappenDt()).age(dto.getAge())
                .noticeComment(dto.getNoticeComment())
                .build();
    }

    public boolean isNotNeedUpdate(AnimalProtectDto dto) {

        if (!StringUtils.hasText(dto.getNoticeNo())) {
            return true;
        }

        return dto.getNoticeSdt().equals(this.noticeSdt)
                && dto.getNoticeEdt().equals(this.noticeEdt)
                && dto.getProcessState().equals(this.processState)
                && dto.getCareAddr().equals(this.careAddr)
                && dto.getCareNm().equals(this.careNm)
                && dto.getCareTel().equals(this.careTel)
                && dto.getOfficetel().equals(this.officeTel)
                && dto.getSpecialMark().equals(this.specialMark);
    }
}
