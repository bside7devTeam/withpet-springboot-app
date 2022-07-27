package org.gig.withpet.core.domain.community.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gig.withpet.core.domain.community.comment.CommunityComment;
import org.gig.withpet.core.domain.community.community.dto.CommunityDto;
import org.gig.withpet.core.domain.user.member.Member;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Getter
public class CommentDto {

    private String comment;
    private Long commentId;
    private Long parentId;
    private Long communityId;
    private User writer;
    private List<String> images;

    public CommentDto(CommunityComment comment) {
        this.comment = comment.getComment();
        this.commentId = comment.getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.communityId = comment.getCommunity().getId();
        this.writer = new User(comment.getWriter());
    }

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

    @Getter
    public static class Request {
        private String comment;
        private Long parentId;
        private Long communityId;

        public boolean hasParent() {
            return this.parentId != null;
        }
    }
}
