package org.gig.withpet.core.domain.alarmMessage.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.alarmMessage.MessageType;

/**
 * @author : JAKE
 * @date : 2022/08/06
 */
@Getter
public class MessageDto {

    private String receiver;

    private String title;

    private String content;

    private String imageUrl;

    private MessageType messageType;

    private Long senderMemberId;

    private Long receiverMemberId;

    private Long senderAdminId;
}
