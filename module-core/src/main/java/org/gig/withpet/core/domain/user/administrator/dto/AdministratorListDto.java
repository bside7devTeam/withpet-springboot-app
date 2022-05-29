package org.gig.withpet.core.domain.user.administrator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gig.withpet.core.domain.user.administrator.Administrator;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@Getter
@NoArgsConstructor
public class AdministratorListDto extends AdministratorDto {

    private String createdByUsername;

    public AdministratorListDto(Administrator a) {
        super(a);
        if (a.getCreatedBy() != null) {
            this.createdByUsername = a.getCreatedBy().getUsername();
        }
    }
}
