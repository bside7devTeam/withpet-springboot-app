package org.gig.withpet.core.domain.user.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;

}
