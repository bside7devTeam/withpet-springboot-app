package org.gig.withpet.core.domain.user.member.dto;

import lombok.Getter;
import org.gig.withpet.core.domain.common.types.YnType;
import org.gig.withpet.core.domain.user.member.types.SnsType;

@Getter
public class SignUpRequest {
    public String uid;
    public String email;
    public SnsType snsType;
    private String nickname;
    private String profileImage;
    private String sido;
    private String sigungo;
    private String emd;
    private YnType agreeOver14Yn;
    private YnType agreePolicyYn;
    private YnType agreePrivacyYn;
    private YnType agreeMarketingYn;
}
