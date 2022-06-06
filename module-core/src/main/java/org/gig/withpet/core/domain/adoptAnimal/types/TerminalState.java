package org.gig.withpet.core.domain.adoptAnimal.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TerminalState {

    ADOPT("adopt", "입양"),

    EUTHANASIA("euthanasia", "안락사"),

    NATURAL_DEATH("natural_death", "자연사"),

    RETURN("return", "반환"),

    RADIATION("radiation", "방사"),

    ENDOWMENT("endowment", "기증"),

    ETC("etc", "기타");

    private String key;

    private String description;
}
