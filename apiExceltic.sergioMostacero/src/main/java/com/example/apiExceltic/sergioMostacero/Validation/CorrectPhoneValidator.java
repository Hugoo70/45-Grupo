package com.example.apiExceltic.sergioMostacero.Validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CorrectPhoneValidator implements ConstraintValidator<CorrectNumber,String> {
 
@Override
public void initialize(CorrectNumber phoneNumber){
}

@Override
public boolean isValid(String phoneNumber, ConstraintValidatorContext cxt){

    if(phoneNumber == null){
        return false;
    }
    return phoneNumber.matches("\\d{8,14}");
}
}
