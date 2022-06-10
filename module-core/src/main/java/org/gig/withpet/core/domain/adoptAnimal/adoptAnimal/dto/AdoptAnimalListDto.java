package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@NoArgsConstructor
public class AdoptAnimalListDto extends AdoptAnimalDto {

    public AdoptAnimalListDto(AdoptAnimal a) {
        super(a);
    }
}
