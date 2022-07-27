package org.gig.withpet.core.domain.community.commentAttachment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.attachment.Attachment;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.community.comment.CommunityComment;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityCommentAttachment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_comment_attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommunityComment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    private String fullPath;

    public static CommunityCommentAttachment addAttachment(CommunityComment comment, Attachment attachment, String fullPath) {
        return CommunityCommentAttachment.builder()
                .comment(comment)
                .attachment(attachment)
                .fullPath(fullPath)
                .build();
    }
}
