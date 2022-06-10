package org.gig.withpet.core.domain.user.member.dto;

import lombok.Data;
import org.gig.withpet.core.domain.user.member.domain.SnsType;

@Data
public class SignInRequest {
    public String uid;
    public String email;
    public SnsType snsType;
}
