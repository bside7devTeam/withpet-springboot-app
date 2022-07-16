package org.gig.withpet.core.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/07/16
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {

    int page = 0;

    int size = 10;
}
