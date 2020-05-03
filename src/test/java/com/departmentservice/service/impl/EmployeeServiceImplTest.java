package com.departmentservice.service.impl;

import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Department department = new Department();
        Employee boss = new Employee();
        boss.setDepartment(department);
        boss.setBoss(true);
        boss.setFirstName("Григорий");
        Employee employee = new Employee();
        employee.setDepartment(department);
        Mockito.when(employeeRepository.getBossOfEmployee(2L)).thenReturn(boss);
    }

    @Test
    void getBossOfEmployee() {
        System.out.println(employeeService.getBossOfEmployee(2L));
    }
}