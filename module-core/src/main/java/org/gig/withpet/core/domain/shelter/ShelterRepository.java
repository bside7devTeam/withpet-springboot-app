package org.gig.withpet.core.domain.shelter;

import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/05
 */
@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    Optional<Shelter> findByAdoptAnimalRegNoAndDeleteYn(String regNo, YnType deleteYn);

    List<Shelter> findAllByDeleteYn(YnType deleteYn);

    Optional<Shelter> findByInstitutionCodeAndDeleteYn(String institutionCode, YnType deleteYn);

}
