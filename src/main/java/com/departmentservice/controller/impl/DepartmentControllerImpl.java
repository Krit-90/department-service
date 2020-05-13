package com.departmentservice.controller.impl;

import com.departmentservice.controller.DepartmentController;
import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("departments")
@RestController
public class DepartmentControllerImpl implements DepartmentController {
    DepartmentService departmentService;

    @Autowired
    public DepartmentControllerImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public DepartmentDto getDepartment(Long id) {
        return departmentService.getDepartmentInfoById(id);

    }

    public ResponseEntity addDepartment(DepartmentDtoReceive departmentDto) {
        departmentService.addDepartment(departmentDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity removeDepartment(Long id) {
        departmentService.removeDepartment(id);
        return ResponseEntity.ok("Объект удален либо не существует");
    }

    @Override
    public List<DepartmentDto> getHigherDepartment(Long id) {
        return departmentService.getAllHigherDepartments(id);
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> getAllSubDepartment(Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getAllSubordinateDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> getSubDepartment(Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getSubordinateDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }

    @Override
    public ResponseEntity changeHeadDepartment(Long idNewHead, Long idCurrent) {
        Department department = departmentService.changeHeadDepartment(idNewHead, idCurrent);
        return ResponseEntity.ok(department);
    }

    @Override
    public ResponseEntity getSumOfSalary(Long id) {
        return ResponseEntity.ok(departmentService.getSumOfSalary(id));
    }

    @Override
    public ResponseEntity getEmployeesOfDepartment(Long id) {
        List<EmployeeDto> employeeDto = departmentService.getEmployeesOfDepartment(id);
        return ResponseEntity.ok(employeeDto);
    }

    @Override
    public ResponseEntity changeTitle(String newTitle, Long id) {
        return ResponseEntity.ok(departmentService.updateDepartmentTitle(newTitle, id));
    }

}
