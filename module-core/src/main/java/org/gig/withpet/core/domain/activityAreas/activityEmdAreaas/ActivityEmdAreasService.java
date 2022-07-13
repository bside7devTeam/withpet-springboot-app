package org.gig.withpet.core.domain.activityAreas.activityEmdAreaas;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : JAKE
 * @date : 2022/07/11
 */
@Service
@RequiredArgsConstructor
public class ActivityEmdAreasService {

    private final ActivityEmdAreasRepository activityEmdAreasRepository;

    public void save(ActivityEmdAreas activityEmdAreas) {
        activityEmdAreasRepository.save(activityEmdAreas);
    }

}
