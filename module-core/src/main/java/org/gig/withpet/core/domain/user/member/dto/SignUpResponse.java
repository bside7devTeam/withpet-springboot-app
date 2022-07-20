package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.role.Role;
import org.gig.withpet.core.domain.user.member.Member;
import org.gig.withpet.core.domain.user.member.MemberRole;
import org.gig.withpet.core.domain.user.member.types.SnsType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class SignUpResponse {
    private String uid;
    private String nickname;
    private String profileImage;
    private SnsType snsType;
    private TokenDto token;
    private List<String> roles;

    public SignUpResponse(Member member) {
        this.uid = member.getUid();
        this.roles = member.getRoleNames();
        this.nickname = member.getNickName();
        this.snsType = member.getSnsType();
        this.profileImage = member.getProfileImage();
    }

    public void setToken(String accessToken, String refreshToken) {
        this.token = new TokenDto(accessToken, refreshToken);
    }
}
