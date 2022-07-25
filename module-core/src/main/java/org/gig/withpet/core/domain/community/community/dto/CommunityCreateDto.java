package org.gig.withpet.core.domain.community.community.dto;

import lombok.Data;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.community.community.types.CategoryType;

import java.util.List;

@Data
public class CommunityCreateDto {
    private CategoryType categoryType;
    private String title;
    private String content;
    private List<ImageModel> images;
}
