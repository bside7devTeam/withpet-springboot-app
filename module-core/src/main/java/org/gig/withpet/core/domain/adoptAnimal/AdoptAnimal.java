package org.gig.withpet.core.domain.adoptAnimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.dto.AnimalProtectDto;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;

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

    private String processState;

    private String desertionNo;

    private String noticeNo;

    private String noticeSdt;

    private String noticeEdt;

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

    public static AdoptAnimal insertPublicData(AnimalProtectDto dto) {
        return AdoptAnimal.builder()
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
}
