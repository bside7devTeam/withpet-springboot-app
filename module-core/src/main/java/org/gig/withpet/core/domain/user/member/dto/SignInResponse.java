package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.user.member.Member;

@Getter
public class SignInResponse {
    private String uid;
    private String role;

    public SignInResponse(Member member) {
        this.uid = member.getUid();
        this.role = member.getRoleType().toString();
    }
}
