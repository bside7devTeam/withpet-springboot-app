package org.gig.withpet.core.domain.activityAreas.activityAreas;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/11
 */
@Service
@RequiredArgsConstructor
public class ActivityAreasService {

    private final ActivityAreasRepository activityAreasRepository;

    public List<ActivityAreas> getActivityAreasByMember(Member member) {
        return activityAreasRepository.findAllByMemberAndDeleteYn(member, YnType.N);
    }

    public void saveActivityArea(Member member, String region1DepthName, String region2DepthName, String region3DepthName) throws Exception {

        List<ActivityAreas> activityAreas = activityAreasRepository.findAllByMemberAndDeleteYn(member, YnType.N);
        if (activityAreas.size() > 2) {
            throw new Exception("회원의 활동 지역은 최대 2개까지만 가능합니다.");
        }

        if (CollectionUtils.isEmpty(activityAreas)) {



            return;
        }



    }
}
