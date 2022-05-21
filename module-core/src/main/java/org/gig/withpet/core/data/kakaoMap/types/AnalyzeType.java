package org.gig.withpet.core.data.kakaoMap.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : JAKE
 * @date : 2022/05/21
 */
@Getter
@RequiredArgsConstructor
public enum AnalyzeType {

    Similar("similar", "유사"),
    Exact("exact", "정확");

    final private String value;
    final private String description;
}
