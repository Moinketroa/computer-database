package com.excilys.computerdatabase.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.excilys.computerdatabase.validator.DateIntegrityValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateIntegrityValidator.class)
public @interface DateIntegrity {
  String message() default "The discontinuation date is prior to the introduction date";

  String introduction();

  String discontinuation();
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
