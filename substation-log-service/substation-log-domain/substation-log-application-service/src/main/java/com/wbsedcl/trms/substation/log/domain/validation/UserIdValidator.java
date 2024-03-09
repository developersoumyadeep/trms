package com.wbsedcl.trms.substation.log.domain.validation;

import com.wbsedcl.trms.substation.log.domain.entity.User;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
@Slf4j
public class UserIdValidator implements ConstraintValidator<ValidUserId, String> {

    private final UserRepository userRepository;

    public UserIdValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidUserId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userIdToValidate, ConstraintValidatorContext context) {
        if (userIdToValidate == null) {
            return false;
        }
        Optional<User> user = userRepository.findUser(userIdToValidate);
        if(user.isEmpty()) {
            log.error("AuthenticatedUser with id {} does not exist", userIdToValidate);
            return false;
        }
        return true;
    }
}
