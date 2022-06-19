package org.gig.withpet.core.domain.user.member.dto;

import lombok.Data;
import org.gig.withpet.core.domain.user.member.domain.Member;

@Data
public class SignInResponseDto {
    private String uid;
    private String role;

    public SignInResponseDto(Member member) {
        uid = member.getUid();
        role = member.getRoleType().toString();
    }
}
