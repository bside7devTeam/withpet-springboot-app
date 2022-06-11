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
public enum ProcessStatus {

    NOTICE("notice", "공고중"),

    PROTECT("protect", "보호중"),

    TERMINAL("terminal", "종료"),

    UNKNOWN("unknown", "unknown");

    private String key;

    private String description;

    public static ProcessStatus findByCodeAndNoticeEndDate(String code, LocalDate noticeEndDate) {
        try {
            if (code.equals("보호중")) {
                if (LocalDate.now().isAfter(noticeEndDate)) {
                    return ProcessStatus.PROTECT;
                } else {
                    return ProcessStatus.NOTICE;
                }
            }

            if (code.contains("종료")) {
                return ProcessStatus.TERMINAL;
            }

            return ProcessStatus.UNKNOWN;
        } catch (IllegalArgumentException e) {
            return ProcessStatus.UNKNOWN;
        }
    }
}
