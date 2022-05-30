package org.gig.withpet.core.domain.user.administrator;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.dto.AdministratorCreateForm;
import org.gig.withpet.core.domain.role.Role;
import org.gig.withpet.core.domain.user.AbstractUser;
import org.gig.withpet.core.domain.user.UserStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2022/05/22
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Administrator extends AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @Builder.Default
    @OneToMany(mappedBy = "administrator", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<AdministratorRole> administratorRoles = new HashSet<>();

    @Override
    public Set<Role> getRoles() {
        return administratorRoles.stream().map(AdministratorRole::getRole).collect(Collectors.toSet());
    }

    public static Administrator initAdministrator(String username, String password, String name) {
        return Administrator.builder()
                .username(username)
                .password(password)
                .name(name)
                .status(UserStatus.NORMAL)
                .build();
    }

    public static Administrator create(AdministratorCreateForm createForm, String encodedPassword) {
        return Administrator.builder()
                .username(createForm.getUsername())
                .name(createForm.getName())
                .password(encodedPassword)
                .passwordFailureCount(0)
                .status(createForm.getStatus())
                .build();
    }

    public void createAdministratorRoles(List<Role> roles) {
        roles.stream().map(role -> AdministratorRole.addAdministratorRole(this, role))
                .forEach(administratorRole -> this.getAdministratorRoles().add(administratorRole));
    }

    public void addRole(AdministratorRole role) {
        this.administratorRoles.add(role);
    }
}
