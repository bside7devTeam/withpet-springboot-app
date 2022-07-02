package org.gig.withpet.core.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.post.domain.CategoryType;
import org.gig.withpet.core.domain.post.domain.Post;
import org.gig.withpet.core.domain.user.member.domain.Member;

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
    private Boolean isLike;
    private User writer;
    private List<String> images;

    public static class User {
        public Long userId;
        public String name;
        public String thumbnail;

        public User(Member member) {
            this.userId = member.getId();
            this.name = member.getNickName();
            this.thumbnail = null; //TODO
        }
    }

    public PostDto(Post post) {
        this.postId = post.getId();
        this.categoryType = post.getCategoryType();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.commentCount = 0; //TODO
        this.likeCount = 0; //TODO
        this.isLike = false; //TODO
        this.writer = new User(post.getWriter());
        this.images = null; //TODO
    }
}
