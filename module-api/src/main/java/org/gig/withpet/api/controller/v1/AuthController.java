package org.gig.withpet.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.user.member.dto.SignInRequest;
import org.gig.withpet.core.domain.user.member.dto.SignInResponse;
import org.gig.withpet.core.domain.user.member.dto.TokenResponse;
import org.gig.withpet.core.domain.user.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @PostMapping("/member/login")
    public ApiResponse login(@RequestBody SignInRequest signInRequest) {
        SignInResponse member = memberService.signIn(signInRequest);
        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUid(), member.getRole());
        memberService.updateRefreshToken(member.getUid(), refreshToken);

        return ApiResponse.OK(new TokenResponse(accessToken, refreshToken));

    }

    @PostMapping("/member/logout")
    public ApiResponse logout() {
        return ApiResponse.OK("logout");
    }

    @PostMapping("/token-refresh")
    public ApiResponse accessTokenRefresh() {
        return ApiResponse.OK("token-refresh");
    }
}
