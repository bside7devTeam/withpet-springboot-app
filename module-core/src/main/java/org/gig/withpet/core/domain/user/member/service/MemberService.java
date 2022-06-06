package org.gig.withpet.core.domain.user.member.service;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.user.member.domain.Member;
import org.gig.withpet.core.domain.user.member.dto.SignInRequest;
import org.gig.withpet.core.domain.user.member.dto.SignInResponse;
import org.gig.withpet.core.domain.user.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public SignInResponse signIn(SignInRequest signInRequest) {
        Optional<Member> member = memberRepository.findByUid(signInRequest.uid);
        return member.map(SignInResponse::new)
                .orElseGet(() -> signUp(signInRequest));

    }

    private SignInResponse signUp(SignInRequest signInRequest) {
        Member member = Member.builder()
                .uid(signInRequest.uid)
                .email(signInRequest.email)
                .snsType(signInRequest.snsType)
                .build();

        return new SignInResponse(memberRepository.save(member));
    }

    public void updateRefreshToken(String uid, String refreshToken) {
        Member member = getMemberByUid(uid);
        member.updateRefreshToken(refreshToken);
    }

    public void logout(String uid) {
        Member member = getMemberByUid(uid);
        member.deleteRefreshToken();
    }

    public SignInResponse compareToken(String uid, String token) {
        Member member = getMemberByUid(uid);
        if (!member.compareToken(token))
            throw new RuntimeException();

        return new SignInResponse(member);
    }

    @Transactional(readOnly = true)
    public Member getMemberByUid(String uid) {
        return memberRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException());
    }
}
