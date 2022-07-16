package org.gig.withpet.core.data.animalProtect.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : JAKE
 * @date : 2022/06/01
 */
@Getter
@Setter
@NoArgsConstructor
public class AnimalProtectDto {

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

    private String officetel;

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

    public LocalDate getNoticeEndDate() {
        return LocalDate.parse(this.noticeEdt, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public LocalDate getNoticeStartDate() {
        return LocalDate.parse(this.noticeEdt, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public LocalDate getHappenDate() {
        return LocalDate.parse(this.happenDt, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
