package org.gig.withpet.core.domain.community.communityLikeMember;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.community.community.Community;
import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author : JAKE
 * @date : 2022/07/31
 */
@Service
@RequiredArgsConstructor
public class CommunityLikeMemberService {

    private final CommunityLikeMemberRepository communityLikeMemberRepository;

    @Transactional
    public boolean like(Community community, Member writer) {

        Optional<CommunityLikeMember> findLike = communityLikeMemberRepository
                .findByCommunityAndMember(community, writer);

        if (findLike.isEmpty()) {
            CommunityLikeMember communityLikeMember = CommunityLikeMember
                    .create(community, writer);
            communityLikeMemberRepository.save(communityLikeMember);
            return true;
        }

        CommunityLikeMember like = findLike.get();
        communityLikeMemberRepository.delete(like);
        return false;
    }
}
