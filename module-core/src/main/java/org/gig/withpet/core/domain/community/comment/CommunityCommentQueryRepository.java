package org.gig.withpet.core.domain.community.comment;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.types.YnType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.gig.withpet.core.domain.community.comment.QCommunityComment.communityComment;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CommunityComment> getCommentPage(Long communityId, Pageable pageable) {

        QueryResults<CommunityComment> result = this.queryFactory
                .selectFrom(communityComment)
                .where(
                        notDeleted(),
                        eqCommunityId(communityId),
                        notParent()
                )
                .orderBy(communityComment.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Page<CommunityComment> getChildCommentPage(Long communityId, Long parentId, PageRequest pageable) {

        QueryResults<CommunityComment> result = this.queryFactory
                .selectFrom(communityComment)
                .where(
                        notDeleted(),
                        eqCommunityId(communityId),
                        eqParentId(parentId)
                )
                .orderBy(communityComment.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public long getTotalCommentsCount(Long communityId) {
        long count = queryFactory.selectFrom(communityComment)
                .where(
                        notDeleted(),
                        eqCommunityId(communityId)
                )
                .fetchCount();

        return count;
    }

    public long getChildCommentCount(Long parentId) {
        long count = queryFactory.selectFrom(communityComment)
                .where(
                        notDeleted(),
                        eqParentId(parentId)
                )
                .fetchCount();

        return count;
    }

    private BooleanExpression eqCommunityId(Long communityId) {
        if (communityId == null) {
            return null;
        }

        return communityComment.community.id.eq(communityId);
    }

    private BooleanExpression eqParentId(Long parentId) {
        if (parentId == null) {
            return null;
        }

        return communityComment.parent.id.eq(parentId);
    }

    private BooleanExpression notParent() {
        return communityComment.parent.isNull();
    }

    private BooleanExpression notDeleted() {
        return communityComment.deleteYn.eq(YnType.N);
    }
}
