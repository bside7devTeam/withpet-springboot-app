package org.gig.withpet.core.domain.alarmMessage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/08/06
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AlarmMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_message_id")
    private Long id;

    private String receiver;

    @Column(length = 1024)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 1024)
    private String imageUrl;

//    /**
//     * JSON 형태
//     */
//    @Column(columnDefinition = "TEXT")
//    private String extraData;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

//    @Embedded
//    private AlarmResponse response;

    private String alarmTemplateId;

    private String emailTemplateId;

    private String returnUrl;

    /**
     * 송신자 ID log용
     */
    private Long senderMemberId;

    /**
     * 수신자 ID log용
     */
    private Long receiverMemberId;

    /**
     * 관리자 발신자 ID log용
     */
    private Long senderAdminId;

    /**
     * 해당 알람의 타입 (디자인에필요)
     */
//    @Enumerated(EnumType.STRING)
//    private AlarmTemplateType alarmTemplateType;

}
