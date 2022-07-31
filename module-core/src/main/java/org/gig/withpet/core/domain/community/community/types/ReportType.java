package org.gig.withpet.core.domain.community.community.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Getter
@AllArgsConstructor
public enum ReportType {

    Swear("swear", "욕설,비방,차별,혐오"),
    Profit("profit", "홍보,영 목적"),
    Illegal("illegal", "불법 정보"),
    harmToYouth("harmToYouth", "음란, 청소년 유해"),
    privacy("privacy", "개인정보 노출,유포,거래"),
    Gambling("gambling", "도박,스팸"),
    Etc("etc", "기타");

    final private String type;
    final private String description;
}
