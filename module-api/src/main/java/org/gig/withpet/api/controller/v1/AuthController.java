package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.config.jwt.JwtTokenProvider;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.data.kakaoMap.KakaoMapApiService;
import org.gig.withpet.core.domain.common.dto.response.AddressResponse;
import org.gig.withpet.core.domain.exception.NotFoundException;
import org.gig.withpet.core.domain.user.member.dto.*;
import org.gig.withpet.core.domain.user.member.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Api(value = "인증 API V1")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final KakaoMapApiService kakaoMapApiService;

    @ApiOperation(value = "회원가입 여부 API")
    @GetMapping("/member/{uid}")
    public ResponseEntity<ApiResponse> checkIsMember(@PathVariable("uid") String uid) {
        Map<String, String> data = authService.getMemberByUid(uid);
        return new ResponseEntity<>(ApiResponse.OK(data), HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입 API")
    @PostMapping("/member/sign-up")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequest signUpRequestDto) {
        SignUpResponse res = null;
        try {
            res = authService.signUp(signUpRequestDto);
            String accessToken = jwtTokenProvider.createAccessToken(res.getUid(), res.getRoles());
            String refreshToken = jwtTokenProvider.createRefreshToken(res.getUid(), res.getRoles());
            res.setToken(accessToken, refreshToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(ApiResponse.OK(res), HttpStatus.OK);
    }

    /**
     * 서로 다른 SNS 계정으로 가입하려 하는 경우
     * @param signInRequestDto
     * @return
     */
    @ApiOperation(value = "로그인 API")
    @PostMapping("/member/login")
    public ResponseEntity<ApiResponse> login(@RequestBody SignInRequest signInRequestDto) {

        try {
            MemberDto member = authService.getMemberDtoByUid(signInRequestDto.uid);
            String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRoles());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.getUid(), member.getRoles());
            authService.logIn(member.getUid(), refreshToken);

            return new ResponseEntity<>(ApiResponse.OK(new TokenDto(accessToken, refreshToken)), HttpStatus.OK);
        } catch (NotFoundException ne) {
            ne.printStackTrace();
            return new ResponseEntity<>(ApiResponse.ERROR(HttpStatus.NOT_FOUND, ne.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/member/logout")
    public ResponseEntity<ApiResponse> logout(Principal principal) {
        authService.logout(principal.getName());
        return new ResponseEntity<>(ApiResponse.OK("logout"), HttpStatus.OK);
    }

    @ApiOperation(value = "Access Token 갱신 API")
    @PostMapping("/token-refresh")
    public ResponseEntity<ApiResponse> accessTokenRefresh(
            Principal principal,
            @RequestHeader(value="Authorization") String token) {
        SignInResponse member =
                authService.compareToken(principal.getName(), jwtTokenProvider.tokenParsing(token));

        String accessToken = jwtTokenProvider.createAccessToken(member.getUid(), member.getRoles());
        return new ResponseEntity<>(ApiResponse.OK(new TokenDto(accessToken)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "좌표로 주소 검색 API"
    )
    @GetMapping(value = "/member/coord/address", produces="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAddressByCoord(
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude
    ) throws IOException {
        AddressResponse data = kakaoMapApiService.getAddressByCoord(latitude, longitude);
        return new ResponseEntity<>(ApiResponse.OK(data), HttpStatus.OK);
    }
}
