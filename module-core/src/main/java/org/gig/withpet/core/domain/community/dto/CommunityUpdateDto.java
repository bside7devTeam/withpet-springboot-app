package org.gig.withpet.core.domain.community.dto;

import lombok.Data;
import org.gig.withpet.core.domain.common.image.ImageModel;

import java.util.List;

@Data
public class CommunityUpdateDto {
    private Long postId;
    private String title;
    private String content;
    private List<ImageModel> images;
}
