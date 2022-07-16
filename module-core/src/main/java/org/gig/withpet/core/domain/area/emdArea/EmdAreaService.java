package org.gig.withpet.core.domain.area.emdArea;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/11
 */
@Service
@RequiredArgsConstructor
public class EmdAreaService {

    private final EmdAreaQueryRepository queryRepository;

    public EmdArea getEmdAreaByRegionNames(String region1DepthName, String region2DepthName, String region3DepthName) throws Exception {

        Optional<EmdArea> findEmdArea = queryRepository.getEmdAreaByRegionNames(region1DepthName, region2DepthName, region3DepthName);
        if (findEmdArea.isEmpty()) {
            throw new Exception("좌표에 해당하는 위치 정보가 없습니다.");
        }

        return findEmdArea.get();
    }
}
