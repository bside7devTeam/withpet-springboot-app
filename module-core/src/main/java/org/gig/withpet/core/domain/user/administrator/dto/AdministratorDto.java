package org.gig.withpet.core.domain.user.administrator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.administrator.Administrator;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class AdministratorDto {

    private Long adminId;

    private String username;

    private String name;

    private UserStatus status;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AdministratorDto(Administrator a) {
        this.adminId = a.getId();
        this.username = a.getUsername();
        this.name = a.getName();
        this.status = a.getStatus();
        this.lastLoginAt = a.getLastLoginAt();
        this.createdAt = a.getCreatedAt();
        this.updatedAt = a.getUpdatedAt();
    }

}
