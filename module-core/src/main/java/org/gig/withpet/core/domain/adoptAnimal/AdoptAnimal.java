package org.gig.withpet.core.domain.adoptAnimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.data.animalProtect.AnimalProtectDto;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.administrator.Administrator;

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

    private String sexCd;

    private String kindCd;

    private String noticeNo;

    private String careAddr;

    private String processState;

    private String noticeSdt;

    private String weight;

    private String chargeNm;

    private String careNm;

    private String desertionNo;

    private String careTel;

    private String happenPlace;

    private String officeTel;

    private String orgNm;

    private String filename;

    private String popfile;

    private String noticeEdt;

    private String neuterYn;

    private String specialMark;

    private String colorCd;

    private String happenDt;

    private String age;

    private String noticeComment;

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
