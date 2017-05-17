package com.cruz.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HashtagValidator.class)
@Documented
public @interface ValidHashtag {
	String message() default "Invalid Hashtag";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
