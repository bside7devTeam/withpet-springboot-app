package org.gig.withpet.core.domain.role;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : JAKE
 * @date : 2022/05/22
 */
@Entity
@Getter @SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Builder.Default
    private int sortOrder = 0;

    public static Role createRole(String name, String description, int sortOrder) {
        return Role.builder()
                .name(name)
                .description(description)
                .sortOrder(sortOrder)
                .build();
    }
}
