package org.gig.withpet.core.domain.community.community.dto;

import lombok.Data;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.user.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommunityDto {
    private Long postId;
    private CategoryType categoryType;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer commentCount;
    private Integer likeCount;
    private YnType likeYn;
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

    public CommunityDto(Community post) {
        this.postId = post.getId();
        this.categoryType = post.getCategoryType();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.commentCount = 0; //TODO
        this.likeCount = 0; //TODO
        this.likeYn = YnType.N; //TODO
        this.writer = new User(post.getWriter());
        this.images = null; //TODO
    }
}
