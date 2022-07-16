package org.gig.withpet.core.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class BasePageDto {

    private String keyword;

    private int page = 0;

    private int size = 10;

    public PageRequest getPageRequest() {
        return PageRequest.of(this.page, this.size);
    }
}
