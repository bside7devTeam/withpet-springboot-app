package org.gig.withpet.core.domain.community.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.commentAttachment.CommunityCommentAttachment;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.user.member.Member;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "delete_yn='N'")
public class CommunityComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommunityComment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<CommunityComment> child = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommunityCommentAttachment> commentAttachments = new ArrayList<>();

    public static CommunityComment create(String comment, Community community, Member writer) {
        return CommunityComment.builder()
                .comment(comment)
                .community(community)
                .writer(writer)
                .build();
    }

    public static CommunityComment createChild(String comment, Community community, CommunityComment parent, Member writer) {
        return CommunityComment.builder()
                .comment(comment)
                .community(community)
                .parent(parent)
                .writer(writer)
                .build();
    }

    public void update(String comment) {
        this.comment = comment;
    }

    public void addParent(CommunityComment parent) {
        this.parent = parent;
        parent.getChild().add(this);
    }

    public void addAttachment(CommunityCommentAttachment communityAttachment) {
        this.commentAttachments.add(communityAttachment);
    }

    public boolean isOwner(String loginUId) {
        return loginUId.equals(this.writer.getUid());
    }

    public boolean isCommunity(Long communityId) {
        return communityId.equals(this.community.getId());
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }
}
