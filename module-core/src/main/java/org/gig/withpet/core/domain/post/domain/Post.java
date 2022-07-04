package org.gig.withpet.core.domain.post.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.gig.withpet.core.domain.common.BaseTimeEntity;
import org.gig.withpet.core.domain.common.DefaultEntity;
import org.gig.withpet.core.domain.user.member.domain.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends DefaultEntity {

    @Enumerated(value = EnumType.STRING)
    private CategoryType categoryType;
    private String title;
    private String content;
    private Boolean deleted = false;

    //TODO comment
    //TODO image
    //TODO like

    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;

    @Builder
    public Post(CategoryType categoryType, String title, String content, Boolean deleted, Member writer) {
        this.categoryType = categoryType;
        this.title = title;
        this.content = content;
        this.deleted = deleted;
        this.writer = writer;
    }

    public static Post Of(CategoryType categoryType, Member writer, String title, String content) {
        return Post.builder()
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

        this.deleted = true;
    }
}
