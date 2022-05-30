package org.gig.withpet.core.domain.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.gig.withpet.core.domain.user.UserStatus;
import org.gig.withpet.core.domain.user.UserType;
import org.gig.withpet.core.domain.user.validations.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/05/30
 */
@Getter
@Setter
public class AdministratorCreateForm {

    @UniqueUsername(userType = UserType.ADMIN)
    @Email(message = "올바른 Email을 입력해주세요.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String username;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    private UserStatus status;

    @NotEmpty(message = "역할을 활성화해주세요.")
    private List<String> roleNames;
}