package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

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

    ETC("429900", "기타"),

    UNKNOWN("unknown", "unknown");

    private String key;

    private String description;

    public static List<Map<String, String>> getAnimalKindInfo() {
        return List.of(
                Map.of("code", "PUPPY", "name", PUPPY.description),
                Map.of("code", "CAT", "name", CAT.description),
                Map.of("code", "ETC", "name", ETC.description)
        );
    }

    public static AnimalKindType findByUpKindCd(String upKindCd) {
        try {
            if (!StringUtils.hasText(upKindCd)) {
                return AnimalKindType.UNKNOWN;
            }

            switch (upKindCd) {
                case "417000" :
                    return AnimalKindType.PUPPY;
                case "422400" :
                    return AnimalKindType.CAT;
                case "429900" :
                    return AnimalKindType.ETC;
                default:
                    return AnimalKindType.UNKNOWN;
            }

        } catch (IllegalArgumentException e) {
            return AnimalKindType.UNKNOWN;
        }
    }
}
