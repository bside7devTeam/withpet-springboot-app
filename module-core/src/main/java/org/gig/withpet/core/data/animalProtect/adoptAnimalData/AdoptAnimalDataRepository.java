package org.gig.withpet.core.data.animalProtect.adoptAnimalData;

import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/08
 */
@Repository
public interface AdoptAnimalDataRepository extends JpaRepository<AdoptAnimalData, Long> {

    Optional<AdoptAnimalData> findAdoptAnimalDataByNoticeNoAndDeleteYn(String noticeNo, YnType deleteYn);

}
