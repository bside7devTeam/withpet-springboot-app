package org.gig.withpet.core.domain.user.member;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.role.Role;
import org.gig.withpet.core.domain.user.AbstractUser;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.member.dto.SignUpRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String uid;

    private String email;

    private String nickName;

    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @Enumerated(EnumType.STRING)
    private SnsType snsType;

    private String refreshToken;

    @Column(length = 1000)
    private String profileImage;

    private LocalDateTime ageOver14ConfirmAt;

    private LocalDateTime policyAgreementAt;

    private LocalDateTime privacyAgreementAt;

    private LocalDateTime marketingAgreementAt;

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<MemberRole> memberRoles = new HashSet<>();


    public static Member signUp(SignUpRequest request, String password) {
        return Member.builder()
                .uid(request.getUid())
                .password(password)
                .username(request.getEmail())
                .email(request.getEmail())
                .nickName(request.getNickname())
                .profileImage(request.getProfileImage())
                .snsType(request.getSnsType())
                .status(UserStatus.NORMAL)
                .policyAgreementAt(request.getAgreePolicyYn() == YnType.Y ? LocalDateTime.now() : null)
                .marketingAgreementAt(request.getAgreeMarketingYn() == YnType.Y ? LocalDateTime.now() : null)
                .privacyAgreementAt(request.getAgreePrivacyYn() == YnType.Y ? LocalDateTime.now() : null)
                .ageOver14ConfirmAt(request.getAgreeOver14Yn() == YnType.Y ? LocalDateTime.now() : null)
                .joinedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Set<Role> getRoles() {
        return memberRoles.stream().map(MemberRole::getRole).collect(Collectors.toSet());
    }

    public void addRole(MemberRole role) {
        this.memberRoles.add(role);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public boolean compareToken(String token) {
        return this.refreshToken.equals(token);
    }
}
