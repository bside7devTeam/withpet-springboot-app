package org.gig.withpet.core.domain.user.member;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.activityAreas.activityAreas.ActivityAreasService;
import org.gig.withpet.core.domain.exception.NotFoundException;
import org.gig.withpet.core.domain.role.RoleService;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.member.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleService roleService;
    private final ActivityAreasService activityAreasService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequestDto) throws Exception {

        Member member = Member.signUp(
                signUpRequestDto,
                passwordEncoder.encode(signUpRequestDto.getUid())
        );

        MemberRole memberRole = MemberRole.addMemberRole(member, roleService.findByRoleName("ROLE_USER"));
        member.addRole(memberRole);

        Member savedMember = memberRepository.save(member);

        activityAreasService.saveActivityArea(
                savedMember,
                signUpRequestDto.getSido(),
                signUpRequestDto.getSigungo(),
                signUpRequestDto.getEmd()
        );

        return new SignUpResponse(savedMember);
    }

    @Transactional
    public void logIn(String uid, String refreshToken) {
        Member member = getMemberByUid(uid);
        member.updateRefreshToken(refreshToken);
        member.loginSuccess();
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

    @Transactional(readOnly = true)
    public MemberDto getMemberDtoByUid(String uid) throws NotFoundException {
        Optional<Member> findMember = memberRepository.findByUidAndStatus(uid, UserStatus.NORMAL);
        if (findMember.isEmpty()) {
            throw new NotFoundException("회원 정보가 없습니다.");
        }

        return new MemberDto(findMember.get());
    }


}
