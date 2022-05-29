package org.gig.withpet.core.domain.user.administrator.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.administrator.Administrator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDetailDto extends AdministratorDto {

    private static final AdministratorDetailDto EMPTY;

    static {
        EMPTY = AdministratorDetailDto.builder()
                .empty(true)
                .status(UserStatus.PENDING)
                .build();
    }

    @Builder.Default
    private boolean empty = false;

    @Builder.Default
    List<String> roles = new ArrayList<>();

    public static AdministratorDetailDto emptyDto() {
        return EMPTY;
    }

    public AdministratorDetailDto(Administrator a) {
        super(a);
        this.roles = a.getAdministratorRoles().stream().map(role -> role.getRole().getName()).collect(Collectors.toList());
    }

}