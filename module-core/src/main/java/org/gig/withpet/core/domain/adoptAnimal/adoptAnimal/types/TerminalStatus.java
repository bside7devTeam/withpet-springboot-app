package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TerminalStatus {

    ADOPT("adopt", "입양"),

    EUTHANASIA("euthanasia", "안락사"),

    NATURAL_DEATH("natural_death", "자연사"),

    RETURN("return", "반환"),

    RADIATION("radiation", "방사"),

    ENDOWMENT("endowment", "기증"),

    ETC("etc", "기타");

    private String key;

    private String description;

    public static TerminalStatus findByTerminalState(String code)  {
        if (code.contains("종료")) {
            if (checkContainsYn("입양", code)) {
                return TerminalStatus.ADOPT;
            }

            if (checkContainsYn("안락사", code)) {
                return TerminalStatus.ENDOWMENT;
            }

            if (checkContainsYn("자연사", code)) {
                return TerminalStatus.NATURAL_DEATH;
            }

            if (checkContainsYn("반환", code)) {
                return TerminalStatus.RETURN;
            }

            if (checkContainsYn("방사", code)) {
                return TerminalStatus.RADIATION;
            }

            if (checkContainsYn("기증", code)) {
                return TerminalStatus.ENDOWMENT;
            }

            if (checkContainsYn("기타", code)) {
                return TerminalStatus.ETC;
            }
        }

        return null;
    }

    public static LocalDate getAdoptSuccessDate(String code) {

        if (code.contains("종료") && checkContainsYn("입양", code)) {
            return LocalDate.now();
        }

        return null;
    }

    private static boolean checkContainsYn(String str, String target) {
        return target.contains(str);
    }
}
