package org.gig.withpet.core.domain.user.administrator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/05/24
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
}
