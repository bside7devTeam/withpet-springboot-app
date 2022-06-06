package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.domain.common.BasePageDto;
import org.springframework.data.domain.PageRequest;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@Setter
@Builder
public class AdoptAnimalSearchDto extends BasePageDto {

    private String noticeSdt;

    private String noticeEdt;

    private ProcessStatus processStatus;

    private TerminalStatus terminalStatus;

    @Override
    public PageRequest getPageRequest() {
        return PageRequest.of(getPage(), 3);
    }
}
