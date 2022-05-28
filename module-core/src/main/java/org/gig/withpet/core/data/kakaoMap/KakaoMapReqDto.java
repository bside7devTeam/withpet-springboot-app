package org.gig.withpet.core.data.kakaoMap;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.data.kakaoMap.types.AnalyzeType;

/**
 * @author : JAKE
 * @date : 2022/05/21
 */
@Getter
@Setter
@Builder
public class KakaoMapReqDto {

    private String query;
    private String analyzeType;

    private String x;
    private String y;
    private String inputCoord;
    private String outputCoord;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;
}
