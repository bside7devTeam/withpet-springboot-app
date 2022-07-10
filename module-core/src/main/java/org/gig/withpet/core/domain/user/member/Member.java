package org.gig.withpet.core.domain.user.member;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.role.Role;
import org.gig.withpet.core.domain.user.AbstractUser;
import org.gig.withpet.core.domain.user.UserStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
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

    private LocalDateTime ageConfirmAt;

    private LocalDateTime policyAgreementAt;

    private LocalDateTime privacyAgreementAt;

    private LocalDateTime marketingAgreementAt;

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<MemberRole> memberRoles = new HashSet<>();


    public static Member signUp(String uid, String email, String password, SnsType snsType) {
        return Member.builder()
                .uid(uid)
                .password(password)
                .username(email)
                .email(email)
                .snsType(snsType)
                .status(UserStatus.NORMAL)
                .policyAgreementAt(LocalDateTime.now())
                .marketingAgreementAt(LocalDateTime.now())
                .privacyAgreementAt(LocalDateTime.now())
                .ageConfirmAt(LocalDateTime.now())
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
