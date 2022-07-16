package org.gig.withpet.core.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.common.PageResponseDto;
import org.gig.withpet.core.domain.post.domain.CategoryType;
import org.gig.withpet.core.domain.post.domain.Post;
import org.gig.withpet.core.domain.post.dto.PostCreateDto;
import org.gig.withpet.core.domain.post.dto.PostDto;
import org.gig.withpet.core.domain.post.dto.PostUpdateDto;
import org.gig.withpet.core.domain.user.member.Member;
import org.gig.withpet.core.domain.user.member.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PostFacade {
    private final MemberService memberService;
    private final PostService postService;

    public PostDto create(String uid, PostCreateDto postCreateDto) {
        Member writer = memberService.getMemberByUid(uid);

        return new PostDto(postService.create(writer, postCreateDto));
    }

    public PostDto update(String uid, PostUpdateDto postUpdateDto) {
        Member modifier = memberService.getMemberByUid(uid);

        return new PostDto(postService.update(modifier, postUpdateDto));
    }

    public void delete(String uid, Long postId) {
        Member deleter = memberService.getMemberByUid(uid);

        postService.delete(deleter, postId);
    }

    public PostDto getPost(Long postId) {
        Post post = postService.getPost(postId);

        return new PostDto(post);
    }

    public PageResponseDto<PostDto> getPostList(CategoryType categoryType, Pageable pageable) {
        Page<Post> posts = postService.getPostListByCategoryType(categoryType, pageable);

        return new PageResponseDto<>(
                posts.getPageable().getPageNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getContent().stream().map(PostDto::new)
                        .collect(Collectors.toList()));
    }
}
