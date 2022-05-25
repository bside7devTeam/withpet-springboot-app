package org.gig.withpet.core.domain.utils;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.exception.AlreadyEntity;
import org.gig.withpet.core.domain.role.Role;
import org.gig.withpet.core.domain.role.RoleRepository;
import org.gig.withpet.core.domain.role.RoleService;
import org.gig.withpet.core.domain.user.administrator.AdministratorRepository;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Component
@RequiredArgsConstructor
public class InitUtils {

    private final AdministratorRepository administratorRepository;
    private final RoleRepository roleRepository;

    private final RoleService roleService;
    private final AdministratorService administratorService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {AlreadyEntity.class})
    public void initData() {

        validationAlreadyEntity();

        roleService.initRole("ROLE_SUPER_ADMIN", "슈퍼관리자", 0);
        roleService.initRole("ROLE_ADMIN", "관리자", 1);
        roleService.initRole("ROLE_USER", "사용자", 2);
        roleService.initRole("ROLE_ANONYMOUS", "익명사용자", 3);

        Role superAdminRole = roleService.findByRoleName("ROLE_SUPER_ADMIN");
        Role adminRole = roleService.findByRoleName("ROLE_ADMIN");

        Set<Role> roles = new HashSet<>();
        roles.add(superAdminRole);
        roles.add(adminRole);

        administratorService.initAdmin("admin@withpet.org", passwordEncoder.encode("withpet123$"), "초기관리자", roles);
    }

    private void validationAlreadyEntity() {
        if (administratorRepository.count() > 0) throw new AlreadyEntity(">>> Already Exist Data");
        if (roleRepository.count() > 0) throw new AlreadyEntity(">>> Already Exist Data");
    }
}
