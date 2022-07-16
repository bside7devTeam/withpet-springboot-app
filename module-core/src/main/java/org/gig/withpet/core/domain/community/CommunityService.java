package org.gig.withpet.core.domain.community;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.community.dto.CommunityCreateDto;
import org.gig.withpet.core.domain.community.dto.CommunityUpdateDto;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommunityService {
    private final CommunityRepository postRepository;

    public Community create(Member writer, CommunityCreateDto postCreateDto) {
        Community post = Community.Of(
                postCreateDto.getCategoryType(),
                writer,
                postCreateDto.getTitle(),
                postCreateDto.getContent());

        return postRepository.save(post);
    }

    public Community update(Member modifier, CommunityUpdateDto postUpdateDto) {
        Community post = getPost(postUpdateDto.getPostId());
        post.update(modifier.getUid(), postUpdateDto.getTitle(), postUpdateDto.getContent());

        return post;
    }

    public void delete(Member deleter, Long postId) {
        Community post = getPost(postId);
        post.delete(deleter.getUid());
    }

    @Transactional(readOnly = true)
    public Community getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());
    }

    public Page<Community> getPostListByCategoryType(CategoryType categoryType, Pageable pageable) {
        return postRepository.findByCategoryType(categoryType, pageable);
    }
}
