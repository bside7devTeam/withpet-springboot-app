package org.gig.withpet.core.domain.Area.sidoArea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Repository
public interface SidoAreaRepository extends JpaRepository<SidoArea, Long> {

    Optional<SidoArea> findSidoAreaByAdmCode(String admCode);

    Optional<SidoArea> findSidoAreaByAdmName(String admName);

}
