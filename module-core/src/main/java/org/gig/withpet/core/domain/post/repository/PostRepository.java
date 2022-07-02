package org.gig.withpet.core.domain.post.repository;

import org.gig.withpet.core.domain.post.domain.CategoryType;
import org.gig.withpet.core.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategoryType(CategoryType categoryType, Pageable pageable);
}
