package com.departmentservice.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PersonDateCheckValidator.class)
@Documented
public @interface EmployeeDateCheck {
    String message() default "validate.date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
