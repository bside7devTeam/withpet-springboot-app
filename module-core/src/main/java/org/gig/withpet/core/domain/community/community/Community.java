package org.gig.withpet.core.domain.community.community;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.gig.withpet.core.domain.community.communityAttachment.CommunityAttachment;
import org.gig.withpet.core.domain.user.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CategoryType categoryType;

    private String title;

    private String content;

    @Builder.Default
    @Column(columnDefinition = "varchar(2) default 'N'")
    @Enumerated(EnumType.STRING)
    private YnType deleteYn = YnType.N;

    //TODO comment
    //TODO image
    //TODO like

    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;

    @Builder.Default
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommunityAttachment> communityAttachments = new ArrayList<>();


    @Builder
    public Community(CategoryType categoryType, String title, String content, Boolean deleted, Member writer) {
        this.categoryType = categoryType;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static Community Of(CategoryType categoryType, Member writer, String title, String content) {
        return Community.builder()
                .categoryType(categoryType)
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }


    public void update(String uid, String title, String content) {
        if (!uid.equals(writer.getUid()))
            throw new RuntimeException();

        this.title = title;
        this.content = content;
    }

    public void delete(String uid) {
        if (!uid.equals(writer.getUid()))
            throw new RuntimeException();

        this.deleteYn = YnType.Y;
    }

    public void addAttachment(CommunityAttachment communityAttachment) {
        this.communityAttachments.add(communityAttachment);
    }

}
