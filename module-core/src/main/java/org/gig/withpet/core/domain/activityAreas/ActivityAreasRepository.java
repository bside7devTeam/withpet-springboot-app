package org.gig.withpet.core.domain.activityAreas;

import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/11
 */
@Repository
public interface ActivityAreasRepository extends JpaRepository<ActivityAreas, Long> {

    List<ActivityAreas> findAllByMemberAndDeleteYn(Member member, YnType deleteYn);

}
