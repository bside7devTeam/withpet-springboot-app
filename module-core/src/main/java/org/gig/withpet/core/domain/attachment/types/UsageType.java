package org.gig.withpet.core.domain.attachment.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2022/07/24
 */
@Getter
@AllArgsConstructor
public enum UsageType {

    Profile("profile", "프로필 이미지"),
    Community("community", "커뮤니티 이미지");

    final private String type;
    final private String description;
}
