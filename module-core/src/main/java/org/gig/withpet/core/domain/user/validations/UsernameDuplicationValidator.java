package org.gig.withpet.core.domain.user.validations;

import lombok.RequiredArgsConstructor;
import org.gig.withpet.core.domain.user.UserService;
import org.gig.withpet.core.domain.user.UserType;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author : JAKE
 * @date : 2022/05/30
 */
@Component
@RequiredArgsConstructor
public class UsernameDuplicationValidator implements ConstraintValidator<UniqueUsername, String> {

    private final AdministratorService administratorService;

    private UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        if (constraintAnnotation.userType() == UserType.ADMIN) {
            userService = administratorService;
        } else if (constraintAnnotation.userType() == UserType.USER) {

        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean isExists = userService.existsUsername(value);

        if (isExists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이미 사용 중인 이메일입니다.")
                    .addConstraintViolation();
        }

        return !isExists;
    }
}
