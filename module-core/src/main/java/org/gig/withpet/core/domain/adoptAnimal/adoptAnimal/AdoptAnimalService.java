package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
