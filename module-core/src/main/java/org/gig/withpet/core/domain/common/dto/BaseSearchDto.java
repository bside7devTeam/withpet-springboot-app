package org.gig.withpet.core.domain.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@Getter
@Setter
public class BaseSearchDto {
    private int page = 0;
    private int size = 10;
    private String keyword;
}
