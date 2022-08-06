package org.gig.withpet.core.domain.notification;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.Member;

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
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private Member sender;

    @Column(length = 1024)
    private String content;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType readYn = YnType.N;

    @Column(length = 1024)
    private String imageUrl;

    private String returnUrl;
}
