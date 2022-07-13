package org.gig.withpet.core.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.post.domain.CategoryType;
import org.gig.withpet.core.domain.post.domain.Post;
import org.gig.withpet.core.domain.post.dto.PostCreateDto;
import org.gig.withpet.core.domain.post.dto.PostUpdateDto;
import org.gig.withpet.core.domain.post.repository.PostRepository;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post create(Member writer, PostCreateDto postCreateDto) {
        Post post = Post.Of(
                postCreateDto.getCategoryType(),
                writer,
                postCreateDto.getTitle(),
                postCreateDto.getContent());

        return postRepository.save(post);
    }

    public Post update(Member modifier, PostUpdateDto postUpdateDto) {
        Post post = getPost(postUpdateDto.getPostId());
        post.update(modifier.getUid(), postUpdateDto.getTitle(), postUpdateDto.getContent());

        return post;
    }

    public void delete(Member deleter, Long postId) {
        Post post = getPost(postId);
        post.delete(deleter.getUid());
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());
    }

    public Page<Post> getPostListByCategoryType(CategoryType categoryType, Pageable pageable) {
        return postRepository.findByCategoryType(categoryType, pageable);
    }
}
