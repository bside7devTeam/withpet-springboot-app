package org.gig.withpet.core.data.vWorldAddress.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

/**
 * @author : JAKE
 * @date : 2022/06/19
 */
@Getter
@Setter
@NoArgsConstructor
public class AddressResDto {

    private String geometry;

    private String title;

    private String id;

    private PointDto point;
}
