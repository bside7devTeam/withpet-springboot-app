package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalDetailDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response.AdoptAnimalListResponse;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response.AdoptSuccessResponse;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.response.AnimalKindInfoResponse;
import org.gig.withpet.core.domain.common.PageResponseDto;
import org.gig.withpet.core.domain.common.PageRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public PageResponseDto<AdoptSuccessResponse> getSuccessAdoptAnimalPageDto(PageRequestDto pageRequestDto) {
        Page<AdoptSuccessResponse> pages = queryRepository.getSuccessAdoptAnimalPageDto(PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize()));

        return new PageResponseDto<>(
                pages.getPageable().getPageNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getContent());
    }

    @Transactional(readOnly = true)
    public List<AdoptAnimalListResponse> getAdoptAnimalEmergencyDto() {
        return queryRepository.getAdoptAnimalEmergencyList();
    }

    @Transactional(readOnly = true)
    public Page<AdoptAnimalListDto> getAdoptAnimalPageDto(AdoptAnimalSearchDto searchDto) {
        return queryRepository.getAdoptAnimalPageDto(searchDto);
    }

    @Transactional(readOnly = true)
    public AdoptAnimalDetailDto getDetail(Long adoptAnimalId) {
        Optional<AdoptAnimalDetailDto> detailDto = queryRepository.getDetail(adoptAnimalId);
        return detailDto.orElse(AdoptAnimalDetailDto.emptyDto());
    }

    @Transactional(readOnly = true)
    public List<AnimalKindInfoResponse> getAdoptAnimalKindInfo(AdoptAnimalSearchDto searchDto) {
        return queryRepository.getAdoptAnimalKindInfo(searchDto);
    }
}
