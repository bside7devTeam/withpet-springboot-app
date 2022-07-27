package org.gig.withpet.core.domain.community.community;

import org.gig.withpet.core.domain.community.community.types.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findByCategoryType(CategoryType categoryType, Pageable pageable);
}
