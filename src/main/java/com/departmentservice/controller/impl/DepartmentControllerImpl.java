package com.departmentservice.controller.impl;

import com.departmentservice.controller.DepartmentController;
import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("departments")
@RestController
public class DepartmentControllerImpl implements DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentControllerImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public DepartmentDto getDepartment(Long id) {
        return departmentService.getDepartmentInfoById(id);
    }

    public DepartmentDtoReceive addDepartment(DepartmentDtoReceive departmentDto) {
        departmentService.addDepartment(departmentDto);
        return departmentDto;
    }

    @Override
    public void removeDepartment(Long id) {
        departmentService.removeDepartment(id);
        ResponseEntity.status(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<DepartmentDto> getHigherDepartment(Long id) {
        return departmentService.getAllHigherDepartments(id);
    }

    @Override
    public List<DepartmentDto> getAllSubDepartment(Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getAllSubordinateDepartments(id);
        return departmentDtoList;
    }

    @Override
    public List<DepartmentDto> getSubDepartment(Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getSubordinateDepartments(id);
        return departmentDtoList;
    }

    @Override
    public DepartmentDto changeHeadDepartment(Long idNewHead, Long idCurrent) {
        DepartmentDto department = departmentService.changeHeadDepartment(idNewHead, idCurrent);
        return department;
    }

    @Override
    public BigDecimal getSumOfSalary(Long id) {
        return departmentService.getSumOfSalary(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesOfDepartment(Long id) {
        List<EmployeeDto> employeeDto = departmentService.getEmployeesOfDepartment(id);
        return employeeDto;
    }

    @Override
    public DepartmentDto changeTitle(String newTitle, Long id) {
        return departmentService.updateDepartmentTitle(newTitle, id);
    }

}
