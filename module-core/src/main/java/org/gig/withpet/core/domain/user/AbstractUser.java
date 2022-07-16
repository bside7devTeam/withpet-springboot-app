package org.gig.withpet.core.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.role.Role;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2022/05/22
 */
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractUser extends BaseTimeEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(columnDefinition = "integer default 0")
    private Integer passwordFailureCount = 0;

    private LocalDateTime passwordModifyAt;

    @Builder.Default
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    private LocalDateTime lastLoginAt;

    private LocalDateTime withDrawAt;

    private LocalDateTime joinedAt;

    public abstract Long getId();

    public abstract Set<Role> getRoles();

    public boolean isNormal() {
        return this.status == UserStatus.NORMAL;
    }

    public void increasePasswordFailureCount() {
        this.passwordFailureCount += 1;
    }

    public void loginSuccess() {
        this.lastLoginAt = LocalDateTime.now();
        this.passwordFailureCount = 0;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
