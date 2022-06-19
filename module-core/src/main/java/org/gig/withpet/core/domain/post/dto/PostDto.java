package org.gig.withpet.core.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.post.domain.CategoryType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private Long postId;
    private CategoryType categoryType;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer commentCount;
    private Integer likeCount;
    private Boolean isLike = false;
    private User writer;
    private List<String> images;

    @AllArgsConstructor
    public static class User {
        public Long userId;
        public String name;
        public String thumbnail;
    }
}
