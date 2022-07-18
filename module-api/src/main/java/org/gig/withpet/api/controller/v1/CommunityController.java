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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "게시판 API V1")
@RequiredArgsConstructor
@RequestMapping("communities")
@RestController
public class CommunityController {
    private final CommunityFacade communityFacade;

    @ApiOperation(value = "게시판 조회 (Page)")
    @GetMapping
    public ResponseEntity<ApiResponse> getCommunityList(
            @RequestParam("categoryType") CategoryType categoryType,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        PageResponseDto<CommunityDto> pageRes = communityFacade.getCommunityPageList(categoryType, page, size);
        return new ResponseEntity<>(ApiResponse.OK(pageRes), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 생성")
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CommunityCreateDto createDto,
                                 Principal principal) {
        CommunityDto community = communityFacade.create(principal.getName(), createDto);
        return new ResponseEntity<>(ApiResponse.OK(community), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 수정")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(CommunityUpdateDto postUpdateDto, Principal principal) {
        CommunityDto community = communityFacade.update(principal.getName(), postUpdateDto);

        return new ResponseEntity<>(ApiResponse.OK(community), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 삭제")
    @PostMapping("/{postId}/delete")
    public ResponseEntity<ApiResponse> postDelete(@PathVariable Long postId, Principal principal) {
        communityFacade.delete(principal.getName(), postId);

        return new ResponseEntity<>(ApiResponse.OK(), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 조회")
    @GetMapping("/{communityId}")
    public ResponseEntity<ApiResponse> getPost(@PathVariable Long communityId) {
        CommunityDto community = communityFacade.getCommunity(communityId);

        return new ResponseEntity<>(ApiResponse.OK(community), HttpStatus.OK);
    }
}