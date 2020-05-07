package com.departmentservice.controller;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.service.DepartmentService;
import com.departmentservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public EmployeeDto testGet() {
        return employeeService.getEmployeeInfoById(1L);
    }

    @GetMapping("/dep")
    public DepartmentDto getDep(@RequestParam(name = "id") Long id) {
        return departmentService.getDepartmentInfoById(id);

    }

    @GetMapping("/dep/higherDep")
    public ResponseEntity<List<DepartmentDto>> getHigherDep(@RequestParam(name = "id") Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getAllHigherDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }

    @GetMapping("/dep/allSubDep")
    public ResponseEntity<List<DepartmentDto>> getAllSubDep(@RequestParam(name = "id") Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getAllSubordinateDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }
    @GetMapping("/dep/subDep")
    public ResponseEntity<List<DepartmentDto>> getSubDep(@RequestParam(name = "id") Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getSubordinateDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }

    @PostMapping("/dep")
    public ResponseEntity addDep(@RequestBody Department department, @RequestParam(name = "headDepartment") String
            headTitle) {
        departmentService.addDepartment(department, headTitle);
        return ResponseEntity.ok().build();
    }
}
