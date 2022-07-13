package org.gig.withpet.core.domain.user.member;

import org.gig.withpet.core.domain.user.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUid(String uid);
}
