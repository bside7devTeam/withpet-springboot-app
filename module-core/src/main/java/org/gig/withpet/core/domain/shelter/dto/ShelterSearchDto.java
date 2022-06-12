package org.gig.withpet.core.domain.shelter.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.domain.common.BasePageDto;
import org.springframework.data.domain.PageRequest;

/**
 * @author : JAKE
 * @date : 2022/06/11
 */
@Getter
@Setter
@Builder
public class ShelterSearchDto extends BasePageDto {

    private String sidoCode;

    private String siggCode;

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 3;

    @Override
    public PageRequest getPageRequest() {
        return PageRequest.of(this.page, this.size);
    }

}
