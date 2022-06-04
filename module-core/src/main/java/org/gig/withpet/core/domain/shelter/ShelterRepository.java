package org.gig.withpet.core.domain.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/06/05
 */
@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
