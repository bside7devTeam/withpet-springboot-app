package org.gig.withpet.core.data.vWorldAddress.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : JAKE
 * @date : 2022/06/19
 */
@Getter
@Setter
@Builder
public class VWorldAddressReqDto {

    private String address;

    private String saveYn;

    private String category;
}
