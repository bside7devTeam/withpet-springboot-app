package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.user.member.Member;
import org.gig.withpet.core.domain.user.member.MemberRole;

/**
 * @author : JAKE
 * @date : 2022/07/18
 */
@Getter
public class MemberDto {

    private String uid;
    private String role;

    public MemberDto(Member member) {
        this.uid = member.getUid();
        this.role = member.getRoleType().toString();
    }
}
