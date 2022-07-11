package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2022/07/10
 */
@Getter
public class AddInfoRequestDto {

    private String uid;

    private String nickname;

    private String profileImage;

    private String region1DepthName;

    private String region2DepthName;

    private String region3DepthName;
}
