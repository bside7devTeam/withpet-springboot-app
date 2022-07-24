package org.gig.withpet.core.domain.activityAreas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.area.emdArea.EmdArea;
import org.gig.withpet.core.domain.area.emdArea.EmdAreaService;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityAreasService {


    private final EmdAreaService emdAreaService;
    private final ActivityAreasRepository activityAreasRepository;

    public List<ActivityAreas> getActivityAreasByMember(Member member) {
        return activityAreasRepository.findAllByMemberAndDeleteYn(member, YnType.N);
    }

    @Transactional
    public void saveActivityArea(Member member, String region1DepthName, String region2DepthName, String region3DepthName) throws Exception {

        List<ActivityAreas> activityAreas = activityAreasRepository.findAllByMemberAndDeleteYn(member, YnType.N);
        if (activityAreas.size() > 2) {
            throw new Exception("회원의 활동 지역은 최대 2개까지만 가능합니다.");
        }

        if (!CollectionUtils.isEmpty(activityAreas)) {
            throw new Exception("기존 회원 활동 지역이 존재합니다.");
        }

        EmdArea emdArea = emdAreaService.getEmdAreaByRegionNames(region1DepthName, region2DepthName, region3DepthName);

        ActivityAreas newActivityAreas = ActivityAreas.insertActivityAreas(member, emdArea);
        activityAreasRepository.save(newActivityAreas);
    }
}
