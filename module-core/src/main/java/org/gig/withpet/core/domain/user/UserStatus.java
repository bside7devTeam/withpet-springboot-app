package org.gig.withpet.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/05/22
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
public enum UserStatus {

    PENDING("Pending", "보류"),

    NORMAL("Normal", "정상"),

    WITHDRAW("Withdraw", "탈퇴"),

    INACTIVE("InActive", "비활성화");

    private String key;

    private String description;
}
