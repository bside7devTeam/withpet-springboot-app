package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.common.PageResponseDto;
import org.gig.withpet.core.domain.post.domain.CategoryType;
import org.gig.withpet.core.domain.post.dto.PostCreateDto;
import org.gig.withpet.core.domain.post.dto.PostDto;
import org.gig.withpet.core.domain.post.dto.PostUpdateDto;
import org.gig.withpet.core.domain.post.service.PostFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "게시판 API V1")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostFacade postFacade;

    @ApiOperation(value = "게시판 생성")
    @PostMapping("/post/create")
    public ApiResponse postCreate(PostCreateDto postCreateDto, Principal principal) {
        PostDto post = postFacade.create(principal.getName(), postCreateDto);

        return ApiResponse.OK(post);
    }

    @ApiOperation(value = "게시판 수정")
    @PutMapping("/post/update")
    public ApiResponse postUpdate(PostUpdateDto postUpdateDto, Principal principal) {
        PostDto post = postFacade.update(principal.getName(), postUpdateDto);

        return ApiResponse.OK(post);
    }

    @ApiOperation(value = "게시판 삭제")
    @PostMapping("/delete/{postId}")
    public ApiResponse postDelete(@PathVariable Long postId, Principal principal) {
        postFacade.delete(principal.getName(), postId);

        return ApiResponse.OK();
    }

    @ApiOperation(value = "게시판 조회")
    @GetMapping("/{postId}")
    public ApiResponse getPost(@PathVariable Long postId) {
        PostDto postDto = postFacade.getPost(postId);

        return ApiResponse.OK(postDto);
    }

    @ApiOperation(value = "게시판 조회 (Page)")
    @GetMapping("/posts")
    public ApiResponse getPostList(CategoryType categoryType, Pageable pageable) {
        PageResponseDto<PostDto> pageRes = postFacade.getPostList(categoryType, pageable);

        return ApiResponse.OK(pageRes);
    }
}
