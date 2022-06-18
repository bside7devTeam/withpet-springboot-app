package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.user.member.dto.SignInRequest;
import org.gig.withpet.core.domain.user.member.dto.SignInResponse;
import org.gig.withpet.core.domain.user.member.dto.TokenResponse;
import org.gig.withpet.core.domain.user.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "인증 API V1")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @ApiOperation(value = "로그인/회원가입 API")
    @PostMapping("/member/login")
    public ApiResponse login(@RequestBody SignInRequest signInRequest) {
        SignInResponse member = memberService.signIn(signInRequest);
        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUid(), member.getRole());
        memberService.updateRefreshToken(member.getUid(), refreshToken);

        return ApiResponse.OK(new TokenResponse(accessToken, refreshToken));

    }

    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/member/logout")
    public ApiResponse logout(Principal principal) {
        memberService.logout(principal.getName());
        return ApiResponse.OK("logout");
    }

    @ApiOperation(value = "Access Token 갱신 API")
    @PostMapping("/token-refresh")
    public ApiResponse accessTokenRefresh(
            Principal principal,
            @RequestHeader(value="Authorization") String token) {
        SignInResponse member =
                memberService.compareToken(principal.getName(), jwtTokenProvider.tokenParsing(token));

        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRole());
        return ApiResponse.OK(new TokenResponse(accessToken, null));
    }

    @ApiOperation(value = "회원가입 여부 API")
    @GetMapping("/member")
    public ApiResponse completed(String uid) {
        memberService.getMemberByUid(uid);
        return ApiResponse.OK();
    }
}
