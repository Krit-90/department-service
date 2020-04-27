package com.departmentservice.service;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import com.departmentservice.repository.DepartamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartamentRepository departamentRepository;

    @Override
    public void addDepartment(Department department, String title) {
        Department headDepartment = departamentRepository.findDepartmentByTitle(title);
    }

    @Override
    public void updateDepartmentTitle(String after, String before) {
        departamentRepository.updateTitle(after, before);
    }

    @Override
    public DepartmentDto getDepartmentInfoByTitle(String title) {
        return null;
    }

    @Override
    public List<DepartmentDto> getSubordinateDepartments(String title) {
        return null;
    }

    @Override
    public List<DepartmentDto> getAllSubordinateDepartments(String title) {
        return null;
    }

    @Override
    public void changeHeadDepartment(String current, String newHead) {

    }

    @Override
    public List<DepartmentDto> getAllHigherDepartments(String title) {
        return null;
    }

    @Override
    public Integer sumOfSalary(String title) {
        return null;
    }

    @Override
    public List<Employee> employeesOfDepartment(String title) {
        return null;
    }
}
