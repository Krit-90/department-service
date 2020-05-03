package com.departmentservice.util;

import com.departmentservice.entity.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonDateCheckValidatorTest {

    @Test
    void testDate() {
        Employee employee = new Employee();
        employee.setEmploymentDate(LocalDate.of(1989, 10, 20));
        employee.setBirthDate(LocalDate.of(1990, 10, 20));
    }

}