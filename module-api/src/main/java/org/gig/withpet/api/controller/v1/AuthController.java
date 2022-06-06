package org.gig.withpet.api.controller.v1;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.user.member.dto.SignInRequest;
import org.gig.withpet.core.domain.user.member.dto.SignInResponse;
import org.gig.withpet.core.domain.user.member.dto.TokenResponse;
import org.gig.withpet.core.domain.user.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ApiResponse logout(Principal principal) {
        memberService.logout(principal.getName());
        return ApiResponse.OK("logout");
    }

    @PostMapping("/token-refresh")
    public ApiResponse accessTokenRefresh(
            Principal principal,
            @RequestHeader(value="Authorization") String token) {
        SignInResponse member =
                memberService.compareToken(principal.getName(), jwtTokenProvider.tokenParsing(token));

        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRole());
        return ApiResponse.OK(new TokenResponse(accessToken, null));
    }

    @GetMapping("/member")
    public ApiResponse completed(String uid) {
        memberService.getMemberByUid(uid);
        return ApiResponse.OK();
    }
}
