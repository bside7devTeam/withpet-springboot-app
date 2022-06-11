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
public class ShelterListDto extends ShelterDto {

    private String sidoName;

    private String siggName;

    public ShelterListDto(Shelter s, String sidoName, String siggName) {
        super(s);
        this.sidoName = sidoName;
        this.siggName = siggName;
    }
}
