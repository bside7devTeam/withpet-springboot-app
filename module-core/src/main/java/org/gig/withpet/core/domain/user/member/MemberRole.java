package org.gig.withpet.core.domain.user.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.role.Role;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/07/10
 */
@Entity
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_name")
    private Role role;

    public static MemberRole addMemberRole(Member member, Role role) {
        return MemberRole.builder()
                .member(member)
                .role(role)
                .build();
    }
}
