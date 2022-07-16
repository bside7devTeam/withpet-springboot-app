package org.gig.withpet.core.domain.community;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.dto.response.PageResponseDto;
import org.gig.withpet.core.domain.community.dto.CommunityCreateDto;
import org.gig.withpet.core.domain.community.dto.CommunityDto;
import org.gig.withpet.core.domain.community.dto.CommunityUpdateDto;
import org.gig.withpet.core.domain.user.member.Member;
import org.gig.withpet.core.domain.user.member.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CommunityFacade {
    private final MemberService memberService;
    private final CommunityService postService;

    public CommunityDto create(String uid, CommunityCreateDto postCreateDto) {
        Member writer = memberService.getMemberByUid(uid);

        return new CommunityDto(postService.create(writer, postCreateDto));
    }

    public CommunityDto update(String uid, CommunityUpdateDto postUpdateDto) {
        Member modifier = memberService.getMemberByUid(uid);

        return new CommunityDto(postService.update(modifier, postUpdateDto));
    }

    public void delete(String uid, Long postId) {
        Member deleter = memberService.getMemberByUid(uid);

        postService.delete(deleter, postId);
    }

    public CommunityDto getPost(Long postId) {
        Community post = postService.getPost(postId);

        return new CommunityDto(post);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CommunityDto> getCommunityPageList(CategoryType categoryType, int page, int size) {
        Page<Community> communities = postService.getPostListByCategoryType(categoryType, PageRequest.of(page, size));

        return new PageResponseDto<>(
                communities.getPageable().getPageNumber(),
                communities.getSize(),
                communities.getTotalElements(),
                communities.getContent().stream().map(CommunityDto::new)
                        .collect(Collectors.toList()));
    }
}
