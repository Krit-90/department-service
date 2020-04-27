package com.departmentservice.service.impl;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import com.departmentservice.repository.DepartamentRepository;
import com.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartamentRepository departamentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartamentRepository departamentRepository) {
        this.departamentRepository = departamentRepository;
    }

    @Override
    public void addDepartment(Department department, String title) {
        Department headDepartment = departamentRepository.findDepartmentByTitle(title);
    }

    @Override
    public void updateDepartmentTitle(String after, String before) {
        departamentRepository.updateTitle(after, before);
    }

    @Override
    public DepartmentDto getDepartmentInfoById(Long id) {
        return null;
    }

    @Override
    public List<DepartmentDto> getSubordinateDepartments(Long id) {
        return null;
    }

    @Override
    public List<DepartmentDto> getAllSubordinateDepartments(Long id) {
        return null;
    }

    @Override
    public void changeHeadDepartment(Long idCurrent, Long idNewHead) {

    }

    @Override
    public List<DepartmentDto> getAllHigherDepartments(Long id) {
        return null;
    }

    @Override
    public BigDecimal getSumOfSalary(Long id) {
        return null;
    }

    @Override
    public List<Employee> getEmployeesOfDepartment(Long id) {
        return null;
    }


}
