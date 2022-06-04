package org.gig.withpet.core.domain.adoptAnimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/06/03
 */
@Repository
public interface AdoptAnimalRepository extends JpaRepository<AdoptAnimal, Long> {
}
