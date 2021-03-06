package org.gig.withpet.core.domain.user.administrator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.role.Role;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/05/22
 */
@Entity
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AdministratorRole extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Administrator administrator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_name")
    private Role role;

    public static AdministratorRole addAdministratorRole(Administrator administrator, Role role) {
        return AdministratorRole.builder()
                .administrator(administrator)
                .role(role)
                .build();
    }
}
