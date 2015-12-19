package app.finance.core.validator;

import app.finance.core.model.MonthlyAmortizationSchedule;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

// from http://examples.javacodegeeks.com/enterprise-java/spring/mvc/spring-mvc-form-handling-example/
public class FormValidator implements Validator {

    public boolean supports(Class<?> paramClass) {
        return MonthlyAmortizationSchedule.class.equals(paramClass);
    }

    public void validate(Object obj, Errors errors) {
        MonthlyAmortizationSchedule form = (MonthlyAmortizationSchedule) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "valid.startDate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "initialBalance", "valid.initialBalance");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "interestRate", "valid.interestRate");
        if (!(form.getInterestRate() < -100 || form.getPaymentType() > 200 ) ) {
            errors.rejectValue("interestRate", "valid.interestRateInvalid");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durationInMonths", "valid.durationInMonths");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "futureValue", "valid.futureValue");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentType", "valid.paymentType");
        if (!(form.getPaymentType() == 0 || form.getPaymentType() == 1) ) {
            errors.rejectValue("paymentType", "valid.paymentTypeInvalid");
        }
    }
}
