package com.departmentservice.controller;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("departments")
@RestController
public class DepartmentController {
    DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("{id}")
    public DepartmentDto getDepartment(@PathVariable(name = "id") Long id) {
        return departmentService.getDepartmentInfoById(id);

    }

    @GetMapping("higher-department/{id}")
    public List<DepartmentDto> getHigherDepartment(@PathVariable(name = "id") Long id) {
        return departmentService.getAllHigherDepartments(id);
    }

    @GetMapping("/{id}/all-sub-department")
    public ResponseEntity<List<DepartmentDto>> getAllSubDepartment(@PathVariable(name = "id") Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getAllSubordinateDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }

    @GetMapping("/{id}/sub-department")
    public ResponseEntity<List<DepartmentDto>> getSubDepartment(@PathVariable(name = "id") Long id) {
        List<DepartmentDto> departmentDtoList = departmentService.getSubordinateDepartments(id);
        return ResponseEntity.ok(departmentDtoList);
    }

    @PostMapping("")
    public ResponseEntity addDepartment(@RequestBody DepartmentDtoReceive departmentDto) {
        departmentService.addDepartment(departmentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("test")
    public List<DepartmentDto> testTreeDepartment() {
        return departmentService.getAllSubordinateDepartments(1L);
    }
}
