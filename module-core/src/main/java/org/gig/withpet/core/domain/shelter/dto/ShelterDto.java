package org.gig.withpet.core.domain.shelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gig.withpet.core.domain.shelter.Shelter;

/**
 * @author : JAKE
 * @date : 2022/06/11
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterDto {

    private Long shelterId;

    private String name;

    private String tel;

    private String landAddress;

    private String roadAddress;

    private String rescueAnimal;

    private String beginTime;

    private String endTime;

    private String holiday;

    private String institutionName;

    private String institutionCode;

    private String adoptAnimalRegNo;

    private String coordinateX;

    private String coordinateY;

    private String sidoCode;

    private String siggCode;

    public ShelterDto(Shelter s) {
        this.shelterId = s.getId();
        this.name = s.getName();
        this.tel = s.getTel();
        this.landAddress = s.getLandAddress();
        this.roadAddress = s.getRoadAddress();
        this.rescueAnimal = s.getRescueAnimal();
        this.beginTime = s.getBeginTime();
        this.endTime = s.getEndTime();
        this.holiday = s.getHoliday();
        this.institutionCode = s.getInstitutionCode();
        this.institutionName = s.getInstitutionName();
        this.coordinateX = s.getCoordinateX();
        this.coordinateY = s.getCoordinateY();
        this.adoptAnimalRegNo = s.getAdoptAnimalRegNo();
        this.sidoCode = s.getSidoCode();
        this.siggCode = s.getSiggCode();
    }
}
