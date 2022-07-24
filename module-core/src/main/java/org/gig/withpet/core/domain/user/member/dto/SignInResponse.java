package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.user.member.Member;

import java.util.List;

@Getter
public class SignInResponse {
    private String uid;
    private List<String> roles;

    public SignInResponse(Member member) {
        this.uid = member.getUid();
        this.roles = member.getRoleNames();
    }
}
