package org.gig.withpet.core.domain.user.validations;

import org.gig.withpet.core.domain.user.UserType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : JAKE
 * @date : 2022/05/30
 */
@Documented
@Constraint(validatedBy = UsernameDuplicationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "이미 가입된 이메일입니다.";

    UserType userType() default UserType.USER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
