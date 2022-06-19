package org.gig.withpet.core.domain.post.service;

import org.gig.withpet.core.domain.common.PageDto;
import org.gig.withpet.core.domain.post.domain.CategoryType;
import org.gig.withpet.core.domain.post.dto.PostCreateDto;
import org.gig.withpet.core.domain.post.dto.PostDto;
import org.gig.withpet.core.domain.post.dto.PostUpdateDto;
import org.springframework.data.domain.Pageable;

public class PostFacade {
    public Long create(String email, PostCreateDto postCreateDto) {
    }

    public Long update(String email, PostUpdateDto postUpdateDto) {
    }

    public void delete(String email, Long postId) {
    }

    public PostDto getPost(Long postId) {
    }

    public PageDto<PostDto> getPostList(CategoryType categoryType, Pageable pageable) {
    }
}
