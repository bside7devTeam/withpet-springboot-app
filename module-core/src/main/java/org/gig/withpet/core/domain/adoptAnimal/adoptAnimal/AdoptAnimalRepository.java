package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/03
 */
@Repository
public interface AdoptAnimalRepository extends JpaRepository<AdoptAnimal, Long> {

    Optional<AdoptAnimal> findAdoptAnimalByNoticeNoAndDeleteYn(String noticeNo, YnType deleteYn);
}
