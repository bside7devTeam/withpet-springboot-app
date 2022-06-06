package org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.gig.withpet.core.domain.common.BasePageDto;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@Setter
@Builder
public class AdoptAnimalSearchDto {

    private String noticeSdt;

    private String noticeEdt;

    private ProcessStatus processStatus;

    private TerminalStatus terminalStatus;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 3;

    public PageRequest getPageRequest() {
        return PageRequest.of(this.page, this.size);
    }
}
