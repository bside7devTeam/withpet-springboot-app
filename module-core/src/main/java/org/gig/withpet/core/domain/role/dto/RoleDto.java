package org.gig.withpet.core.domain.role.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gig.withpet.core.domain.role.Role;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@Getter
@NoArgsConstructor
public class RoleDto {

    private String name;

    private String description;

    private int sortOrder;

    public RoleDto(Role r) {
        this.name = r.getName();
        this.description = r.getDescription();
        this.sortOrder = r.getSortOrder();
    }
}
