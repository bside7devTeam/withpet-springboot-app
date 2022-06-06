package org.gig.withpet.core.domain.user.member.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.DefaultEntity;
import org.gig.withpet.core.domain.role.Role;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member extends DefaultEntity {
    private String uid;
    private String email;
    private String nickName;

    @Builder.Default
    private RoleType roleType = RoleType.USER;

    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
