package com.dh.spike.validator;

import com.dh.spike.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Create by DiaoHao on 2020/10/20 22:27
 */
public class isMobileValidator implements ConstraintValidator<isMobile, String>{

    private boolean required = false;

    @Override
    public void initialize(isMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isMobile(s);
        } else {
            if (StringUtils.isEmpty(s)) {
                return false;
            } else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
