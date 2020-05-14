package com.departmentservice.controller.impl;

import com.departmentservice.controller.EmployeeController;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("employees")
@RestController
public class EmployeeControllerImpl implements EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        return employeeService.getEmployeeInfoById(id);
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        return employeeService.addEmployee(employeeDto);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @Override
    public EmployeeDto firedEmployee(Long id, LocalDate firedDate) {
        return employeeService.firedEmployee(id, firedDate);
    }

    @Override
    public EmployeeDto changeDepartmentOfEmployee(Long employeeId, Long newDepartmentId) {
        return employeeService.changeDepartmentOfEmployee(employeeId, newDepartmentId);
    }

    @Override
    public void changeDepartmentOfAllEmployeeFromSame(Long oldDepartmentId, Long newDepartmentId) {
        employeeService.changeDepartmentOfAllEmployeeFromSame(oldDepartmentId, newDepartmentId);
    }

    @Override
    public EmployeeDto getBossOfEmployee(Long id) {
        return employeeService.getBossOfEmployee(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesByLastNameAndFirstName(String lastName, String firstName) {
        return employeeService.getEmployeesByLastNameAndFirstName(lastName, firstName);
    }
}
