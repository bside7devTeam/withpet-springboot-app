package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.user.member.Member;

@Getter
public class SignUpResponse {
    private String uid;
    private String role;
    private String nickname;
    private String profileImage;

    public SignUpResponse(Member member) {
        this.uid = member.getUid();
        this.role = member.getRoleType().toString();
        this.nickname = member.getNickName();
        this.profileImage = member.getProfileImage();
    }
}
