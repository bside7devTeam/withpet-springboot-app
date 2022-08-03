package org.gig.withpet.core.domain.community.comment;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.attachment.Attachment;
import org.gig.withpet.core.domain.attachment.AttachmentService;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.community.comment.dto.CommentDto;
import org.gig.withpet.core.domain.community.commentAttachment.CommunityCommentAttachment;
import org.gig.withpet.core.domain.community.commentAttachment.CommunityCommentAttachmentService;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.exception.ForbiddenException;
import org.gig.withpet.core.exception.NotFoundException;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final AttachmentService attachmentService;
    private final CommunityCommentRepository commentRepository;
    private final CommunityCommentQueryRepository queryRepository;
    private final CommunityCommentAttachmentService commentAttachmentService;

    @Transactional
    public CommunityComment create(CommentDto.Request request, Community community, Member writer) {
        CommunityComment comment = CommunityComment.create(request.getComment(), community, writer);
        commentRepository.save(comment);
        createCommentImages(comment, request.getImages());
        return comment;
    }

    @Transactional
    public CommunityComment createChild(CommentDto.Request request, Community community, Member writer) {
        Optional<CommunityComment> findParent = commentRepository.findByIdAndDeleteYn(request.getParentId(), YnType.N);
        if (findParent.isEmpty()) {
            throw new NotFoundException("해당 댓글을 찾을 수 없습니다.");
        }
        CommunityComment comment = CommunityComment.createChild(request.getComment(), community, findParent.get(), writer);
        commentRepository.save(comment);
        createCommentImages(comment, request.getImages());
        return comment;
    }

    @Transactional
    public CommunityComment update(CommentDto.ModifyRequest request, Long commentId, Community community, Member loginUser) {
        Optional<CommunityComment> findComment = commentRepository.findByIdAndDeleteYn(commentId, YnType.N);

        if (findComment.isEmpty()) {
            throw new NotFoundException("해당 댓글을 찾을 수 없습니다.");
        }
        CommunityComment comment = findComment.get();
        if (!comment.isOwner(loginUser.getUid())) {
            throw new ForbiddenException("댓글 수정 권한을 가지고 있지 않습니다.");
        }
        if (!comment.isCommunity(community.getId())) {
            throw new InvalidParameterException("잘못된 요청입니다.");
        }

        comment.update(request.getComment());
        commentRepository.save(comment);

        commentAttachmentService.deleteAttachmentsByComment(comment);
        createCommentImages(comment, request.getImages());

        return comment;
    }

    @Transactional
    public void delete(Long commentId, Community community, Member loginUser) {
        Optional<CommunityComment> findComment = commentRepository.findByIdAndDeleteYn(commentId, YnType.N);

        if (findComment.isEmpty()) {
            throw new NotFoundException("해당 댓글을 찾을 수 없습니다.");
        }
        CommunityComment comment = findComment.get();
        if (!comment.isOwner(loginUser.getUid())) {
            throw new ForbiddenException("댓글 수정 권한을 가지고 있지 않습니다.");
        }
        if (!comment.isCommunity(community.getId())) {
            throw new InvalidParameterException("잘못된 요청입니다.");
        }

        comment.delete();
    }

    @Transactional(readOnly = true)
    public Page<CommunityComment> getCommentPageList(Long communityId, PageRequest pageable) {
        return queryRepository.getCommentPage(communityId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CommunityComment> getChildCommentPageList(Long communityId, Long parentId, PageRequest pageable) {
        return queryRepository.getChildCommentPage(communityId, parentId, pageable);
    }

    @Transactional(readOnly = true)
    public long getTotalCommentCount(Long communityId) {
        return queryRepository.getTotalCommentsCount(communityId);
    }

    @Transactional(readOnly = true)
    public long getChildCommentCount(Long parentId) {
        return queryRepository.getChildCommentCount(parentId);
    }

    private void createCommentImages(CommunityComment comment, List<ImageModel> images) {

        comment.getCommentAttachments().clear();

        for (ImageModel image : images) {
            Attachment attachment = attachmentService.findById(image.getId());
            CommunityCommentAttachment communityAttachment = CommunityCommentAttachment.addAttachment(comment, attachment, image.getFullPath());
            commentAttachmentService.create(communityAttachment);
            comment.addAttachment(communityAttachment);
        }

        commentRepository.save(comment);
    }
}
