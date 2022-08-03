package org.gig.withpet.core.domain.user.administrator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.exception.NotFoundException;
import org.gig.withpet.core.domain.role.Role;
import org.gig.withpet.core.domain.role.RoleService;
import org.gig.withpet.core.domain.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdministratorService implements UserService<Administrator> {

    private final PasswordEncoder passwordEncoder;

    private final AdministratorRepository administratorRepository;
    private final AdministratorQueryRepository queryRepository;
    private final RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public Administrator getUser(String username) {
        return queryRepository.findByUsernameAndRoles(username);
    }

    @Override
    @Transactional
    public void loginSuccess(String username) {
        try {
            Administrator admin = getUser(username);
            admin.loginSuccess();
        } catch (NotFoundException ignore) {
        }
    }

    @Override
    @Transactional
    public void increasePasswordFailureCount(String username) {
        try {
            Administrator admin = getUser(username);
            admin.increasePasswordFailureCount();
        } catch (NotFoundException ignore) {

        }
    }

    @Override
    public boolean existsUsername(String username) {
        return queryRepository.existsByUsername(username);
    }


    @Transactional
    public void initAdmin(String username, String password, String name, Set<Role> roles) {
        Administrator initAdministrator = Administrator.initAdministrator(username, password, name);
        for (Role role : roles) {
            AdministratorRole newRole = AdministratorRole.addAdministratorRole(initAdministrator, role);
            initAdministrator.addRole(newRole);
        }
        administratorRepository.save(initAdministrator);
    }
}
