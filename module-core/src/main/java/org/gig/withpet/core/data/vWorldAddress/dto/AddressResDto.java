package org.gig.withpet.core.data.vWorldAddress.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AddressResDto {

    private String geometry;

    private String title;

    private String id;

    private PointDto point;
}
