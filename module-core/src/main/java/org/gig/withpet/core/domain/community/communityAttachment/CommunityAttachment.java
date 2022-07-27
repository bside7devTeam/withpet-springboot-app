package org.gig.withpet.core.domain.community.communityAttachment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.attachment.Attachment;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.community.community.Community;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2022/07/25
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommunityAttachment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    private String fullPath;

    public static CommunityAttachment addAttachment(Community community, Attachment attachment, String fullPath) {
        return CommunityAttachment.builder()
                .community(community)
                .attachment(attachment)
                .fullPath(fullPath)
                .build();
    }
}
