package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response;

import lombok.Getter;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.AnimalKindType;
import org.gig.withpet.core.domain.adoptAnimal.animalKind.AnimalKind;

/**
 * @author : JAKE
 * @date : 2022/07/13
 */
@Getter
public class AnimalKindInfoResponse {

    private AnimalKindType animalKindType;

    private String kindName;

    private String kindCd;

    public AnimalKindInfoResponse(AnimalKind ak) {
        this.animalKindType = AnimalKindType.findByUpKindCd(ak.getUpKindCd());
        this.kindName = ak.getKNm();
        this.kindCd = ak.getKindCd();
    }
}
