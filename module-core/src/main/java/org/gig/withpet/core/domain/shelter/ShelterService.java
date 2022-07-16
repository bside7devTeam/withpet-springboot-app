package org.gig.withpet.core.domain.shelter;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.shelter.dto.ShelterListDto;
import org.gig.withpet.core.domain.shelter.dto.ShelterSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2022/06/11
 */
@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterQueryRepository queryRepository;

    @Transactional(readOnly = true)
    public Page<ShelterListDto> getShelterPageDto(ShelterSearchDto reqParam) {
        return queryRepository.getShelterPageDto(reqParam);
    }
}
