package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalDetailDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdoptAnimalService {

    private final AdoptAnimalQueryRepository queryRepository;


    @Transactional(readOnly = true)
    public Page<AdoptAnimalListDto> getAdoptAnimalPageDto(AdoptAnimalSearchDto searchDto) {
        return queryRepository.getAdoptAnimalPageDto(searchDto);
    }

    @Transactional(readOnly = true)
    public Page<AdoptAnimalListDto> getSuccessAdoptAnimalPageDto(AdoptAnimalSearchDto searchDto) {
        return queryRepository.getSuccessAdoptAnimalPageDto(searchDto);
    }

    @Transactional(readOnly = true)
    public AdoptAnimalDetailDto getDetail(Long adoptAnimalId) {
        Optional<AdoptAnimalDetailDto> detailDto = queryRepository.getDetail(adoptAnimalId);
        return detailDto.orElse(AdoptAnimalDetailDto.emptyDto());
    }
}
