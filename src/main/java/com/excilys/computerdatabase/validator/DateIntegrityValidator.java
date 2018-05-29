package com.excilys.computerdatabase.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.excilys.computerdatabase.validator.annotation.DateIntegrity;

public class DateIntegrityValidator implements ConstraintValidator<DateIntegrity, Object> {

  private String introduction;
  private String discontinuation;

  @Override
  public void initialize(DateIntegrity constraintAnnotation) {
    this.introduction = constraintAnnotation.introduction();
    this.discontinuation = constraintAnnotation.discontinuation();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    LocalDate introductionValue = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(introduction);
    LocalDate discontinuationValue = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(discontinuation);

    if (discontinuationValue == null || introductionValue == null) {
      return true;
    }

    return discontinuationValue.isAfter(introductionValue);
  }

}
