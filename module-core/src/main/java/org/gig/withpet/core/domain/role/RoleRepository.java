package org.gig.withpet.core.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

    List<Role> findAllByOrderBySortOrderAsc();

    List<Role> findAllByNameIn(List<String> roleNames);

    Optional<Role> findByName(String name);
}
