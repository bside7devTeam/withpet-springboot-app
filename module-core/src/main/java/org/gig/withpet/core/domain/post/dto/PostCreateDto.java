package org.gig.withpet.core.domain.post.dto;

import lombok.Data;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.post.domain.CategoryType;

import java.util.List;

@Data
public class PostCreateDto {
    private CategoryType categoryType;
    private String title;
    private String content;
    private List<ImageModel> images;
}
