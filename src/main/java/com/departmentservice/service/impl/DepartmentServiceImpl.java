package com.departmentservice.service.impl;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.repository.DepartmentRepository;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.DepartmentService;
import com.departmentservice.util.MapperEntity;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final MapperEntity mapperEntity;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository,
                                 MapperEntity mapperEntity) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.mapperEntity = Mappers.getMapper(MapperEntity.class);
    }

    @Override
    public Department addDepartment(Department department, String title) {
        department.setHeadDepartment(departmentRepository.findDepartmentByTitle(title));
        departmentRepository.save(department);
        return department;
    }

    @Override
    public Department updateDepartmentTitle(String after, Long id) {
        if (isPresent(id)) {
            departmentRepository.updateTitle(after, id);
        }
        return departmentRepository.findById(id).get();
    }

    @Override
    public void removeDepartment(Long id) {
        if(isPresent(id)){
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public DepartmentDto getDepartmentInfoById(Long id) {
        if (isPresent(id)) {
            return mapDepartmentToDepartmentDto(departmentRepository.findById(id).get());
        }
        return null;
    }

    private boolean isPresent(Long id) {
        if (departmentRepository.findById(id).isPresent()) {
            return true;
        } else {
            throw new NoSuchElementException("Данный департамент не нейден");
        }
    }

    @Override
    public List<DepartmentDto> getSubordinateDepartments(Long id) {
        if (isPresent(id)) {
            List<DepartmentDto> departmentsDtoList = new ArrayList();
            departmentRepository.getSubDepartments(id).
                    forEach(department -> departmentsDtoList.add(mapDepartmentToDepartmentDto(department)));
            return departmentsDtoList;
        }
        return null;
    }

    @Override
    public List<DepartmentDto> getAllSubordinateDepartments(Long id) {
        return null;
    }

    @Override
    public Department changeHeadDepartment(Long idCurrent, Long idNewHead) {
        if (isPresent(idCurrent) & isPresent(idNewHead)) {
            departmentRepository.changeHeadDepartment(idCurrent, idNewHead);
            return departmentRepository.findById(idCurrent).get();
        }
        return null;
    }

    @Override
    public Department getDepartmentByTitle(String title) {
        return departmentRepository.findDepartmentByTitle(title);
    }

    @Override
    public List<DepartmentDto> getAllHigherDepartments(Long id) {
        return null;
    }

    @Override
    public BigDecimal getSumOfSalary(Long id) {
        if (isPresent(id)) {
            return employeeRepository.getSumOfSalaryInDepartment(id);
        }
        return null;
    }

    @Override
    public List<EmployeeDto> getEmployeesOfDepartment(Long id) {
        if (isPresent(id)) {
            List<EmployeeDto> employeesDtoList = new ArrayList();
            employeeRepository.getEmployeesFromDepartment(id).
                    forEach(employee -> employeesDtoList.add(mapperEntity.employeeToDto(employee)));
        }
        return null;
    }

    @Override
    public DepartmentDto mapDepartmentToDepartmentDto(Department department) {
        DepartmentDto departmentDto = mapperEntity.departmentToDto(department);
        departmentDto.setBoss(mapperEntity.employeeToDto(employeeRepository.getBoss(department.getId())));
        departmentDto.setCountEmployee(employeeRepository.getCountOfEmployeesInDepartment(department.getId()));
        return null;
    }
}
