package org.gig.withpet.core.domain.community.comment;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.dto.response.PageResponseDto;
import org.gig.withpet.core.domain.community.comment.dto.CommentDto;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.community.community.CommunityFacade;
import org.gig.withpet.core.domain.user.member.AuthService;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public PageResponseDto<CommentDto> getCommentPageList(Long communityId, int page, int size) {

        Page<CommunityComment> comments = commentService.getCommentPageList(communityId, PageRequest.of(page, size));

        return new PageResponseDto<>(
                comments.getPageable().getPageNumber(),
                comments.getSize(),
                comments.getTotalElements(),
                comments.getContent().stream().map(
                        comment -> new CommentDto(comment, commentService.getChildCommentCount(comment.getId())))
                        .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CommentDto> getChildCommentPageList(Long communityId, Long commentId, int page, int size) {

        Page<CommunityComment> comments = commentService.getChildCommentPageList(communityId, commentId, PageRequest.of(page, size));
        return new PageResponseDto<>(
                comments.getPageable().getPageNumber(),
                comments.getSize(),
                comments.getTotalElements(),
                comments.getContent().stream().map(CommentDto::new)
                        .collect(Collectors.toList()));
    }

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
