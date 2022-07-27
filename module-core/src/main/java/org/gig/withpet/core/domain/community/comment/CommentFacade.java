package org.gig.withpet.core.domain.community.comment;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.community.comment.dto.CommentDto;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.community.community.CommunityFacade;
import org.gig.withpet.core.domain.user.member.AuthService;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final AuthService authService;
    private final CommunityFacade communityFacade;
    private final CommentService commentService;

    @Transactional
    public CommentDto create(CommentDto.Request request) {
        Member writer = authService.getLoginUser();
        Community community = communityFacade.getCommunity(request.getCommunityId());

        CommunityComment comment = null;
        if (request.hasParent()) {
            comment = commentService.createChild(request, community, writer);
        } else {
            comment = commentService.create(request, community, writer);
        }

        return new CommentDto(comment);
    }
}
