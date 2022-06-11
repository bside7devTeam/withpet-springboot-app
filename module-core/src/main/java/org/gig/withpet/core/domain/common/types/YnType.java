package org.gig.withpet.core.domain.common.types;

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
public enum YnType {

    Y("Y", "Y"),

    N("N", "N");

    private String key;

    private String description;

    public static YnType findByCode(String code) {
        try {
            return YnType.valueOf(code);
        } catch (IllegalArgumentException e) {
            return YnType.N;
        }
    }
}

