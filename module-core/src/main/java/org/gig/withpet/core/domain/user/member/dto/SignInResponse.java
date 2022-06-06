package org.gig.withpet.core.domain.user.member.dto;

import lombok.Data;
import org.gig.withpet.core.domain.user.member.domain.Member;

@Data
public class SignInResponse {
    private String uid;
    private String role;

    public SignInResponse(Member member) {
        uid = member.getUid();
        role = member.getRoleType().toString();
    }
}
