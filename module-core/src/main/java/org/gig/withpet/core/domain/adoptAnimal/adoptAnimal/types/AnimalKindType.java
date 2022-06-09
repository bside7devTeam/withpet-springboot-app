package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/06/08
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AnimalKindType {

    PUPPY("417000", "강아지"),

    CAT("422400", "고양이"),

    ETC("429900", "기타");

    private String key;

    private String description;
}
