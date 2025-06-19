package org.koreait.survey.diabetes.validators;

import org.koreait.survey.diabetes.controllers.RequestDiabetesSurvey;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class DiabetesSurveyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestDiabetesSurvey.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestDiabetesSurvey form = (RequestDiabetesSurvey) target;
        String mode = form.getMode();
        if (StringUtils.hasText(mode) && mode.equals("step2")){
            validataStep2(form,errors);
        } else {
            validataStep1(form,errors);
        }
    }

    private void validataStep1(RequestDiabetesSurvey form, Errors errors){
        int age = form.getAge();
        if (age < 5 || age > 130){
            errors.rejectValue("age","Size");
        }
    }

    private void validataStep2(RequestDiabetesSurvey form, Errors errors){
        double height = form.getHeight();
        if (height < 50.0 || height > 300.0){
            errors.rejectValue("height","Size");
        }
        double weight = form.getWeight();
        if (weight < 5.0 || weight > 700.0){
            errors.rejectValue("weight","Size");
        }
        double HbA1c = form.getHbA1c();
        if (HbA1c < 0.0 || HbA1c > 100.0){
            errors.rejectValue("HbA1c","Size");
        }
        double bloodGlucoseLevel = form.getBloodGlucoseLevel();
        if (bloodGlucoseLevel < 0.0 || bloodGlucoseLevel > 2700.0){
            errors.rejectValue("bloodGlucoseLevel","Size");
        }
    }
}
