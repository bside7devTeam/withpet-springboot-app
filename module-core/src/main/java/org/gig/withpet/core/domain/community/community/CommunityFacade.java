package org.gig.withpet.core.domain.community.community;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.dto.response.PageResponseDto;
import org.gig.withpet.core.domain.community.community.dto.CommunityCreateDto;
import org.gig.withpet.core.domain.community.community.dto.CommunityDto;
import org.gig.withpet.core.domain.community.community.dto.CommunityUpdateDto;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.gig.withpet.core.domain.community.community.types.CommunitySearchType;
import org.gig.withpet.core.domain.user.member.Member;
import org.gig.withpet.core.domain.user.member.AuthService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CommunityFacade {
    private final AuthService authService;
    private final CommunityService communityService;

    public CommunityDto create(CommunityCreateDto postCreateDto) {
        Member writer = authService.getLoginUser();
        Community newCommunity = communityService.create(writer, postCreateDto);

        return new CommunityDto(newCommunity);
    }

    public CommunityDto update(String uid, CommunityUpdateDto postUpdateDto) {
        Member modifier = authService.getUser(uid);

        return new CommunityDto(communityService.update(modifier, postUpdateDto));
    }

    public void delete(Long postId) {
        Member deleter = authService.getLoginUser();
        communityService.delete(deleter, postId);
    }

    public CommunityDto getCommunity(Long postId) {
        Community community = communityService.getCommunity(postId);
        return new CommunityDto(community);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<CommunityDto> getCommunityPageList(CommunitySearchType searchType, CategoryType categoryType, int page, int size) {
        Page<Community> communities = null;

        Member me = authService.getLoginUser();
        List<Long> emdIds = me.getEmdIds();

        if (searchType == CommunitySearchType.TOWN) {
            communities = communityService.getPostListByMyTown(categoryType, emdIds, PageRequest.of(page, size));
        } else {
            communities = communityService.getPostListByCategoryType(categoryType, PageRequest.of(page, size));
        }

        return new PageResponseDto<>(
                communities.getPageable().getPageNumber(),
                communities.getSize(),
                communities.getTotalElements(),
                communities.getContent().stream().map(CommunityDto::new)
                        .collect(Collectors.toList()));
    }
}
