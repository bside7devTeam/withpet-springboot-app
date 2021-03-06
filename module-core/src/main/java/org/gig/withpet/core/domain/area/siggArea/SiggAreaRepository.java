package org.gig.withpet.core.domain.area.siggArea;

import org.gig.withpet.core.domain.area.sidoArea.SidoArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Repository
public interface SiggAreaRepository extends JpaRepository<SiggArea, Long> {

    List<SiggArea> findAllBySido(SidoArea sido);

    Optional<SiggArea> findSiggAreaByAdmName(String admName);
}
