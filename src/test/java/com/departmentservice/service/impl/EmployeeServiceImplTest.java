package com.departmentservice.service.impl;

import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import com.departmentservice.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class EmployeeServiceImplTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Department department = new Department();
        department.setId(2L);
        Employee boss = new Employee();
        boss.setDepartment(department);
        boss.setBoss(true);
        boss.setFirstName("Григорий");
        Employee employee = new Employee();
        employee.setDepartment(department);
        Mockito.when(employeeRepository.getBossOfDepartment(2L)).thenReturn(boss);
    }

    @Test
    void getBossOfEmployee() {
        System.out.println(employeeService.getBossOfEmployee(2L));
    }
}