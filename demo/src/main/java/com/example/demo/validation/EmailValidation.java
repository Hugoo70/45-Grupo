package com.example.demo.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public void initialize(EmailConstraint email) {

    }

    @Override
    public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
        if (emailField == null) {
            return false;
        }

        return emailField.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}
