package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.user.member.Member;
import org.gig.withpet.core.domain.user.member.SnsType;

@Getter
public class SignUpResponse {
    private String uid;
    private String role;
    private String nickname;
    private String profileImage;
    private SnsType snsType;
    private TokenDto token;

    public SignUpResponse(Member member) {
        this.uid = member.getUid();
        this.role = member.getRoleType().toString();
        this.nickname = member.getNickName();
        this.snsType = member.getSnsType();
        this.profileImage = member.getProfileImage();
    }

    public void setToken(String accessToken, String refreshToken) {
        this.token = new TokenDto(accessToken, refreshToken);
    }
}
