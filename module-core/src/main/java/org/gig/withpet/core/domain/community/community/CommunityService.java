package org.gig.withpet.core.domain.community.community;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.attachment.Attachment;
import org.gig.withpet.core.domain.attachment.AttachmentService;
import org.gig.withpet.core.domain.common.image.ImageModel;
import org.gig.withpet.core.domain.community.community.dto.CommunityCreateDto;
import org.gig.withpet.core.domain.community.community.dto.CommunityUpdateDto;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.gig.withpet.core.domain.community.communityImage.CommunityAttachment;
import org.gig.withpet.core.domain.community.communityImage.CommunityAttachmentService;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityQueryRepository queryRepository;
    private final AttachmentService attachmentService;
    private final CommunityAttachmentService communityAttachmentService;

    @Transactional(readOnly = true)
    public Page<Community> getPostListByCategoryType(CategoryType categoryType, Pageable pageable) {
        return queryRepository.getCommunityPage(categoryType, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Community> getPostListByMyTown(CategoryType categoryType, List<Long> emdIds, Pageable pageable) {
        return queryRepository.getCommunityMyTownPage(categoryType, emdIds, pageable);
    }

    @Transactional
    public Community create(Member writer, CommunityCreateDto communityCreateDto) {

        Community community = Community.Of(
                communityCreateDto.getCategoryType(),
                writer,
                communityCreateDto.getTitle(),
                communityCreateDto.getContent()
        );

        communityRepository.save(community);

        for (ImageModel image : communityCreateDto.getImages()) {
            Attachment attachment = attachmentService.findById(image.getId());
            CommunityAttachment communityAttachment = CommunityAttachment.addAttachment(community, attachment, image.getFullPath());
            communityAttachmentService.create(communityAttachment);
        }

        return communityRepository.save(community);
    }

    public Community update(Member modifier, CommunityUpdateDto postUpdateDto) {
        Community post = getCommunity(postUpdateDto.getPostId());
        post.update(modifier.getUid(), postUpdateDto.getTitle(), postUpdateDto.getContent());

        return post;
    }

    public void delete(Member deleter, Long postId) {
        Community post = getCommunity(postId);
        post.delete(deleter.getUid());
    }

    @Transactional(readOnly = true)
    public Community getCommunity(Long postId) {
        return communityRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());
    }
}
