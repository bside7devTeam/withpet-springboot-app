package org.gig.withpet.core.domain.user.member;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.activityAreas.activityAreas.ActivityAreasService;
import org.gig.withpet.core.domain.role.RoleService;
import org.gig.withpet.core.domain.user.member.dto.AddInfoRequestDto;
import org.gig.withpet.core.domain.user.member.dto.SignInRequestDto;
import org.gig.withpet.core.domain.user.member.dto.SignInResponseDto;
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

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Optional<Member> member = memberRepository.findByUid(signInRequestDto.uid);
        return member.map(SignInResponseDto::new)
                .orElseGet(() -> signUp(signInRequestDto));
    }

    private SignInResponseDto signUp(SignInRequestDto signInRequestDto) {

        Member member = Member.signUp(
                signInRequestDto.uid,
                signInRequestDto.email,
                passwordEncoder.encode(signInRequestDto.getUid()),
                signInRequestDto.snsType
        );

        MemberRole memberRole = MemberRole.addMemberRole(member, roleService.findByRoleName("ROLE_USER"));
        member.addRole(memberRole);

        return new SignInResponseDto(memberRepository.save(member));
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

    public SignInResponseDto compareToken(String uid, String token) {
        Member member = getMemberByUid(uid);
        if (!member.compareToken(token))
            throw new RuntimeException();

        return new SignInResponseDto(member);
    }

    @Transactional
    public Map<String, String> updateAddMemberInfo(AddInfoRequestDto addInfoRequestDto) throws Exception {
        Member member = getMemberByUid(addInfoRequestDto.getUid());
        member.updateAddInfo(addInfoRequestDto);
        activityAreasService.saveActivityArea(
                member,
                addInfoRequestDto.getSido(),
                addInfoRequestDto.getSigungo(),
                addInfoRequestDto.getEmd()
        );

        return Map.of("saveYn", "Y");
    }

    @Transactional(readOnly = true)
    public Member getMemberByUid(String uid) {
        return memberRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException());
    }


}
