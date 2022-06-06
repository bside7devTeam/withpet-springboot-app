package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types;

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
public enum ProcessStatus {

    NOTICE("notice", "공고중"),

    PROTECT("protect", "보호중"),

    TERMINAL("terminal", "종료");

    private String key;

    private String description;
}
