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
import java.time.LocalDate;
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
        department.setCreationDate(LocalDate.now());
        department.setHeadDepartment(departmentRepository.findByTitle(title));
        departmentRepository.save(department);
        return department;
    }

    @Override
    public Department updateDepartmentTitle(String newTitle, Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        department.setTitle(newTitle);
        return departmentRepository.save(department);
    }

    @Override
    public void removeDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDto getDepartmentInfoById(Long id) {
        return enrichDepartmentDto(departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Департамент не найден")));
    }

    private boolean isPresent(Long id) {
        if (departmentRepository.findById(id).isPresent()) {
            return true;
        } else {
            throw new NoSuchElementException("Данный департамент не найден");
        }
    }

    @Override
    public List<DepartmentDto> getSubordinateDepartments(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        List<DepartmentDto> departmentsDtoList = new ArrayList();
        departmentRepository.findByHeadDepartmentId(id).
                forEach(department -> departmentsDtoList.add(enrichDepartmentDto(department)));
        return departmentsDtoList;
    }

    @Override
    public List<DepartmentDto> getAllSubordinateDepartments(Long id) {
        return null;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Department changeHeadDepartment(Long idCurrent, Long idNewHead) {
        departmentRepository.findById(idCurrent).orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        departmentRepository.findById(idNewHead).orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        departmentRepository.changeHeadDepartment(idCurrent, idNewHead);
        return departmentRepository.findById(idCurrent).get();
    }

    @Override
    public Department getDepartmentByTitle(String title) {
        return departmentRepository.findByTitle(title);
    }

    @Override
    public List<DepartmentDto> getAllHigherDepartments(Long id) {
        return null;
    }

    @Override
    public BigDecimal getSumOfSalary(Long id) {
        departmentRepository.findById(id).orElseThrow();
        return employeeRepository.getSumOfSalaryInDepartment(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesOfDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        List<EmployeeDto> employeesDtoList = new ArrayList();
        employeeRepository.findByDepartmentId(id).
                forEach(employee -> employeesDtoList.add(mapperEntity.employeeToDto(employee)));
        return employeesDtoList;
    }

    //TODO: Если делать приватным, то его нужно прописывать в интерфейсе, еще и как то автоварить маппер и репозиторий
    // или как то по другому
    @Override
    public DepartmentDto enrichDepartmentDto(Department department) {
        DepartmentDto departmentDto = mapperEntity.departmentToDto(department);
        departmentDto.setBoss(mapperEntity.employeeToDto(employeeRepository.getBoss(department.getId())));
        departmentDto.setCountEmployee(employeeRepository.getCountOfEmployeesInDepartment(department.getId()));
        return null;
    }
}
