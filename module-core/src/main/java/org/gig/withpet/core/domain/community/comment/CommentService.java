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
import org.gig.withpet.core.domain.community.communityAttachment.CommunityAttachment;
import org.gig.withpet.core.domain.exception.NotFoundException;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CommunityCommentAttachmentService commentAttachmentService;

    @Transactional
    public CommunityComment create(CommentDto.Request request, Community community, Member writer) {
        CommunityComment comment = CommunityComment.create(request.getComment(), community, writer);
        commentRepository.save(comment);
        createCommentImages(comment, request.getImages());
        return comment;
    }

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

    private void createCommentImages(CommunityComment comment, List<ImageModel> images) {

        for (ImageModel image : images) {
            Attachment attachment = attachmentService.findById(image.getId());
            CommunityCommentAttachment communityAttachment = CommunityCommentAttachment.addAttachment(comment, attachment, image.getFullPath());
            commentAttachmentService.create(communityAttachment);
            comment.addAttachment(communityAttachment);
        }

        commentRepository.save(comment);
    }
}
