package com.sersun.trello_app.config;

import com.sersun.trello_app.model.Project;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Project> {

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(Project project, ConstraintValidatorContext context) {
        // Perform the validation
        if (project == null || project.getStartDate() == null || project.getEndDate() == null) {
            return true; // Let other validations handle null values
        }

        //return !project.getStartDate().isAfter(project.getEndDate());
        return !project.getStartDate().after(project.getEndDate());
    }
}
