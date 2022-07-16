package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response;

import lombok.Getter;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;

/**
 * @author : JAKE
 * @date : 2022/07/16
 */
@Getter
public class AdoptSuccessResponse {

    private Long id;

    private String imgSrc;

    public AdoptSuccessResponse(AdoptAnimal a) {
        this.id = a.getId();
        this.imgSrc = a.getPopfile();
    }
}
