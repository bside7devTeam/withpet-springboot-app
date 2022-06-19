package org.gig.withpet.core.domain.user.member.service;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.user.member.domain.Member;
import org.gig.withpet.core.domain.user.member.dto.SignInRequestDto;
import org.gig.withpet.core.domain.user.member.dto.SignInResponseDto;
import org.gig.withpet.core.domain.user.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Optional<Member> member = memberRepository.findByUid(signInRequestDto.uid);
        return member.map(SignInResponseDto::new)
                .orElseGet(() -> signUp(signInRequestDto));

    }

    private SignInResponseDto signUp(SignInRequestDto signInRequestDto) {
        Member member = Member.builder()
                .uid(signInRequestDto.uid)
                .email(signInRequestDto.email)
                .snsType(signInRequestDto.snsType)
                .build();

        return new SignInResponseDto(memberRepository.save(member));
    }

    public void updateRefreshToken(String uid, String refreshToken) {
        Member member = getMemberByUid(uid);
        member.updateRefreshToken(refreshToken);
    }

    public void logout(String uid) {
        Member member = getMemberByUid(uid);
        member.deleteRefreshToken();
    }

    public SignInResponseDto compareToken(String uid, String token) {
        Member member = getMemberByUid(uid);
        if (!member.compareToken(token))
            throw new RuntimeException();

        return new SignInResponseDto(member);
    }

    @Transactional(readOnly = true)
    public Member getMemberByUid(String uid) {
        return memberRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException());
    }
}
