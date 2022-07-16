package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.user.member.dto.AddInfoRequestDto;
import org.gig.withpet.core.domain.user.member.dto.SignInRequestDto;
import org.gig.withpet.core.domain.user.member.dto.SignInResponseDto;
import org.gig.withpet.core.domain.user.member.dto.TokenDto;
import org.gig.withpet.core.domain.user.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Api(value = "인증 API V1")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @ApiOperation(value = "로그인/회원가입 API")
    @PostMapping("/member/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto member = memberService.signIn(signInRequestDto);
        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUid(), member.getRole());
        memberService.logIn(member.getUid(), refreshToken);

        return new ResponseEntity<>(ApiResponse.OK(new TokenDto(accessToken, refreshToken)), HttpStatus.OK);

    }

    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/member/logout")
    public ResponseEntity<ApiResponse> logout(Principal principal) {
        memberService.logout(principal.getName());
        return new ResponseEntity<>(ApiResponse.OK("logout"), HttpStatus.OK);
    }

    @ApiOperation(value = "Access Token 갱신 API")
    @PostMapping("/token-refresh")
    public ResponseEntity<ApiResponse> accessTokenRefresh(
            Principal principal,
            @RequestHeader(value="Authorization") String token) {
        SignInResponseDto member =
                memberService.compareToken(principal.getName(), jwtTokenProvider.tokenParsing(token));

        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRole());
        return new ResponseEntity<>(ApiResponse.OK(new TokenDto(accessToken, null)), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 추가정보 입력 API")
    @PostMapping("/member/add-info")
    public ResponseEntity<ApiResponse> memberAddInfo(
            @RequestBody AddInfoRequestDto addInfoRequestDto
        ) {

        Map<String, String> result = null;
        try {
            result = memberService.updateAddMemberInfo(addInfoRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(ApiResponse.OK(result), HttpStatus.OK);
    }



    @ApiOperation(value = "회원가입 여부 API")
    @GetMapping("/member")
    public ResponseEntity<ApiResponse> completed(String uid) {
        memberService.getMemberByUid(uid);
        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }
}
