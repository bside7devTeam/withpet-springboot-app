package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.community.comment.CommentFacade;
import org.gig.withpet.core.domain.community.comment.dto.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 2022/07/27
 */
@Api(value = "게시판 API V1")
@RequiredArgsConstructor
@RequestMapping("communities/comment")
@RestController
public class CommunityCommentController {

    private final CommentFacade commentFacade;

    @ApiOperation(value = "댓글 생성")
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CommentDto.Request request) {
        CommentDto community = commentFacade.create(request);
        return new ResponseEntity<>(ApiResponse.OK(community), HttpStatus.OK);
    }


}
