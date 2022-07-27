package org.gig.withpet.api.controller.v1.community;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.common.dto.response.PageResponseDto;
import org.gig.withpet.core.domain.community.comment.CommentFacade;
import org.gig.withpet.core.domain.community.comment.dto.CommentDto;
import org.gig.withpet.core.domain.community.community.dto.CommunityDto;
import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.gig.withpet.core.domain.community.community.types.CommunitySearchType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Api(value = "게시판 API V1")
@RequiredArgsConstructor
@RequestMapping
@RestController
public class CommentController {

    private final CommentFacade commentFacade;

    @ApiOperation(value = "댓글 조회 (Page)")
    @GetMapping("communities/{communityId}/comments")
    public ResponseEntity<ApiResponse> getCommunityList(
            @PathVariable("communityId") Long communityId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        PageResponseDto<CommentDto> pageRes = commentFacade.getCommentPageList(communityId, page, size);
        return new ResponseEntity<>(ApiResponse.OK(pageRes), HttpStatus.OK);
    }

    @ApiOperation(value = "대댓글 조회 (Page)")
    @GetMapping("communities/{communityId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> getChildCommunityList(
            @PathVariable("communityId") Long communityId,
            @PathVariable("commentId") Long commentId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        PageResponseDto<CommentDto> pageRes = commentFacade.getChildCommentPageList(communityId, commentId, page, size);
        return new ResponseEntity<>(ApiResponse.OK(pageRes), HttpStatus.OK);
    }


    @ApiOperation(value = "댓글 생성")
    @PostMapping("communities/comments")
    public ResponseEntity<ApiResponse> create(@RequestBody CommentDto.Request request) {
        CommentDto community = commentFacade.create(request);
        return new ResponseEntity<>(ApiResponse.OK(community), HttpStatus.OK);
    }


}
