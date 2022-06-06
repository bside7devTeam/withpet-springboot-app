package org.gig.withpet.core.domain.Area.siggArea;

import org.gig.withpet.core.domain.Area.sidoArea.SidoArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/06/04
 */
@Repository
public interface SiggAreaRepository extends JpaRepository<SiggArea, Long> {

    List<SiggArea> findAllBySido(SidoArea sido);
}
