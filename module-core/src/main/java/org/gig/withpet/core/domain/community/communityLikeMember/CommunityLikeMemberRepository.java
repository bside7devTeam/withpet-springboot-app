package org.gig.withpet.core.domain.community.communityLikeMember;

import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Repository
public interface CommunityLikeMemberRepository extends JpaRepository<CommunityLikeMember, Long> {
    Optional<CommunityLikeMember> findByCommunityAndMember(Community community, Member member);
}
