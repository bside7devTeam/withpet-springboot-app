package org.gig.withpet.core.domain.role;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.exception.NotFoundException;
import org.gig.withpet.core.domain.role.dto.RoleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final String prefix = "ROLE_";
    private final RoleRepository roleRepository;

    @Transactional
    public Role initRole(String name, String description, int sortOrder) {
        validationRoleName(name);
        Role newRole = Role.createRole(name, description, sortOrder);
        return roleRepository.save(newRole);
    }

    public Role findByRoleName(String roleName) {
        Optional<Role> findRole = roleRepository.findByName(roleName);
        if (findRole.isPresent()) {
            return findRole.get();
        } else {
            throw new NotFoundException(">>> Role Not Found");
        }
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAllByOrderBySortOrderAsc().stream().map(RoleDto::new).collect(Collectors.toList());
    }

    private void validationRoleName(String name) {
        if (!StringUtils.hasText(name)) { throw new IllegalArgumentException(""); }
        if (existsRoleName(name))  { throw new RuntimeException(); }
        if (!checkedPrefixRoleName(name)) { throw new RuntimeException(); }
    }

    public boolean existsRoleName(String roleName) {
        return roleRepository.existsByName(roleName);
    }

    private boolean checkedPrefixRoleName(String name) {
        return name.toUpperCase().startsWith(prefix);
    }

}
