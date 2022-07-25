package org.gig.withpet.core.domain.community.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.community.communityImage.CommunityAttachment;
import org.gig.withpet.core.domain.user.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommunityDto {
    private Long communityId;
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
            this.thumbnail = member.getProfileImage();
        }
    }

    public CommunityDto(Community community) {
        this.communityId = community.getId();
        this.categoryType = community.getCategoryType();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.createdAt = community.getCreatedAt();
        this.commentCount = 0; //TODO
        this.likeCount = 0; //TODO
        this.likeYn = YnType.N; //TODO
        this.writer = new User(community.getWriter());
        this.images = community.getCommunityAttachments().stream().map(CommunityAttachment::getFullPath).collect(Collectors.toList());
    }
}