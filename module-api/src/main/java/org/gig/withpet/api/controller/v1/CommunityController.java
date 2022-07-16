package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.common.dto.response.PageResponseDto;
import org.gig.withpet.core.domain.community.CategoryType;
import org.gig.withpet.core.domain.community.dto.CommunityCreateDto;
import org.gig.withpet.core.domain.community.dto.CommunityDto;
import org.gig.withpet.core.domain.community.dto.CommunityUpdateDto;
import org.gig.withpet.core.domain.community.CommunityFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "게시판 API V1")
@RequiredArgsConstructor
@RequestMapping("community")
@RestController
public class CommunityController {
    private final CommunityFacade communityFacade;

    @ApiOperation(value = "게시판 조회 (Page)")
    @GetMapping
    public ApiResponse getCommunityList(
            @RequestParam("categoryType") CategoryType categoryType,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        PageResponseDto<CommunityDto> pageRes = communityFacade.getCommunityPageList(categoryType, page, size);
        return ApiResponse.OK(pageRes);
    }

    @ApiOperation(value = "게시판 생성")
    @PostMapping
    public ApiResponse create(CommunityCreateDto createDto, Principal principal) {
        CommunityDto community = communityFacade.create(principal.getName(), createDto);
        return ApiResponse.OK(community);
    }

    @ApiOperation(value = "게시판 수정")
    @PutMapping("/update")
    public ApiResponse postUpdate(CommunityUpdateDto postUpdateDto, Principal principal) {
        CommunityDto post = communityFacade.update(principal.getName(), postUpdateDto);

        return ApiResponse.OK(post);
    }

    @ApiOperation(value = "게시판 삭제")
    @PostMapping("/{postId}/delete")
    public ApiResponse postDelete(@PathVariable Long postId, Principal principal) {
        communityFacade.delete(principal.getName(), postId);

        return ApiResponse.OK();
    }

    @ApiOperation(value = "게시판 조회")
    @GetMapping("/{communityId}")
    public ApiResponse getPost(@PathVariable Long communityId) {
        CommunityDto postDto = communityFacade.getPost(communityId);

        return ApiResponse.OK(postDto);
    }
}
