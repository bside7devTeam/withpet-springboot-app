package org.gig.withpet.core.domain.alarmMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/07/08
 */
@Getter
@RequiredArgsConstructor
public enum MessageType {

    Push("push", "push"),
    Email("email", "email");

    final private String type;
    final private String description;
}
