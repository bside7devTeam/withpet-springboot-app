package org.gig.withpet.core.domain.user.member;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;

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
public class MemberAlarm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_alarm_id")
    private Long id;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType newAdoptAnimal = YnType.N;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType newCommentYn = YnType.N;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType newLikeYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
