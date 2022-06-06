package org.gig.withpet.core.domain.adoptAnimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Repository
public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
}
