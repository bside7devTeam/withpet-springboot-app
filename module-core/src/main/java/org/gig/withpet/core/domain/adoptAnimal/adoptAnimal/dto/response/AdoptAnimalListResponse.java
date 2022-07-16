package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimal;
import org.gig.withpet.core.domain.common.types.YnType;

/**
 * @author : JAKE
 * @date : 2022/07/16
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdoptAnimalListResponse {

    private Long id;

    private String imgSrc;

    private YnType likeYn = YnType.N;

    public AdoptAnimalListResponse(AdoptAnimal a) {
        this.id = a.getId();
        this.imgSrc = a.getPopfile();
    }
}
