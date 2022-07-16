package org.gig.withpet.core.domain.common.dto.request;

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
public class CoordinateRequestDto {

    private String latitude;

    private String longitude;
}
