package org.gig.withpet.core.domain.user.member.dto;

import lombok.Data;
import lombok.Getter;
import org.gig.withpet.core.domain.user.member.SnsType;

@Getter
public class SignInRequestDto {
    public String uid;
    public String email;
    public SnsType snsType;
}
