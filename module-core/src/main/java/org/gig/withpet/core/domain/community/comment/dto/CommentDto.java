package org.gig.withpet.core.domain.community.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.community.comment.CommunityComment;
import org.gig.withpet.core.domain.community.commentAttachment.CommunityCommentAttachment;
import org.gig.withpet.core.domain.user.member.Member;

import java.util.List;
import java.util.stream.Collectors;

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
    private long childCommentCount;

    public CommentDto(CommunityComment comment) {
        this.comment = comment.getComment();
        this.commentId = comment.getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.communityId = comment.getCommunity().getId();
        this.writer = new User(comment.getWriter());
        this.images = comment.getCommentAttachments().stream().map(CommunityCommentAttachment::getFullPath).collect(Collectors.toList());
    }

    public CommentDto(CommunityComment comment, long childCommentCount) {
        this.comment = comment.getComment();
        this.commentId = comment.getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.communityId = comment.getCommunity().getId();
        this.writer = new User(comment.getWriter());
        this.images = comment.getCommentAttachments().stream().map(CommunityCommentAttachment::getFullPath).collect(Collectors.toList());
        this.childCommentCount = childCommentCount;
    }

    public CommentDto(CommunityComment comment, List<CommunityCommentAttachment> attachments) {
        this.comment = comment.getComment();
        this.commentId = comment.getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.communityId = comment.getCommunity().getId();
        this.writer = new User(comment.getWriter());
        this.images = attachments.stream().map(CommunityCommentAttachment::getFullPath).collect(Collectors.toList());
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
    @AllArgsConstructor
    public static class Request {
        private String comment;
        private Long parentId;
        private List<ImageModel> images;

        public boolean hasParent() {
            return this.parentId != null;
        }
    }

    @Getter
    public static class ModifyRequest {
        private String comment;
        private List<ImageModel> images;

        public ModifyRequest(String comment, List<ImageModel> images) {
            this.comment = comment;
            this.images = images;
        }
    }
}
