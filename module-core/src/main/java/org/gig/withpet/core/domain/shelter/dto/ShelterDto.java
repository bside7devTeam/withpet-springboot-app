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

    private String regNo;

    private String sidoCode;

    private String siggCode;

    public ShelterDto(Shelter s) {
        this.shelterId = s.getId();
        this.name = s.getName();
        this.regNo = s.getRegNo();
        this.sidoCode = s.getSidoCode();
        this.siggCode = s.getSiggCode();
    }
}
