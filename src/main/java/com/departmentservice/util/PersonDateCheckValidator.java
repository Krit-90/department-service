package com.departmentservice.util;

import com.departmentservice.entity.Employee;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonDateCheckValidator implements ConstraintValidator<EmployeeDateCheck, Employee> {

    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext context) {
        if (employee.getEmploymentDate().isAfter(employee.getBirthDate())) {
            return true;
        }

        return false;
    }
}
