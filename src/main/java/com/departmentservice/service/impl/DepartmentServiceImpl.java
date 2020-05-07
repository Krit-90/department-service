package com.departmentservice.service.impl;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.exception.NoSuchElementInDBException;
import com.departmentservice.exception.ValidationException;
import com.departmentservice.repository.DepartmentRepository;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.DepartmentService;
import com.departmentservice.util.MapperDepartment;
import com.departmentservice.util.MapperEmployee;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final MapperDepartment mapperDepartment;
    private final MapperEmployee mapperEmployee;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.mapperDepartment = Mappers.getMapper(MapperDepartment.class);
        this.mapperEmployee = Mappers.getMapper(MapperEmployee.class);
    }

    @Override
    public Department addDepartment(Department department, String title) {
        if (departmentRepository.findByTitle(title) == null |
                departmentRepository.getCountOfDepartmentsWhereHeadIsNull() > 1) {
            throw new ValidationException("Вышестоящий департамент не найден или самый верхний уже существует");
        }
        department.setCreationDate(LocalDate.now());
        department.setHeadDepartment(departmentRepository.findByTitle(title));
        departmentRepository.save(department);
        return department;
    }

    @Override
    public Department updateDepartmentTitle(String newTitle, Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        department.setTitle(newTitle);
        return departmentRepository.save(department);
    }

    // TODO: В условии сказано, что удалять нельзя лишь в том случае, если есть работники в нем, а если есть подчиненные
    //  департаменты? ведь у них станет headDepartment null, ставить каскадное удаление?
    @Override
    public void removeDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDto getDepartmentInfoById(Long id) {
        return enrichDepartmentDto(departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден")));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Department changeHeadDepartment(Long idCurrent, Long idNewHead) {
        if (idCurrent.equals(idNewHead)) {
            throw new ValidationException("Департамент не может быть главныи для самого себя");
        }
        departmentRepository.findById(idCurrent).orElseThrow(() ->
                new NoSuchElementInDBException("Департамент не найден"));
        departmentRepository.findById(idNewHead).orElseThrow(() ->
                new NoSuchElementInDBException("Департамент не найден"));
        departmentRepository.changeHeadDepartment(idCurrent, idNewHead);
        return departmentRepository.findById(idCurrent).get();
    }

    @Override
    public Department getDepartmentByTitle(String title) {
        return departmentRepository.findByTitle(title);
    }

    @Override
    public List<DepartmentDto> getAllHigherDepartments(Long id) {
        List<DepartmentDto> higherDepartments = new ArrayList<>();
        Department current = departmentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementInDBException("Дапартамент не найден"));
        current = current.getHeadDepartment();
        while (current != null) {
            higherDepartments.add(enrichDepartmentDto(current));
            current = current.getHeadDepartment();
        }
        return higherDepartments;
    }

    //TODO: StackOverFlow и хоть убей, и в здесь и в просто подчиненных департаментах
    @Override
    public List<DepartmentDto> getAllSubordinateDepartments(Long id) {
/*        Set<Department> currentSubDepartments = departmentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementInDBException("Дапартамент не найден")).getSubDepartments();*/
        List<DepartmentDto> subDepartmentsDto = new ArrayList<>();
        getTreeOfDepartments(departmentRepository.findByHeadDepartmentId(id)).
                forEach(department -> subDepartmentsDto.add(enrichDepartmentDto(department)));
        return subDepartmentsDto;
    }

    private Set<Department> getTreeOfDepartments(Set<Department> headDepartments) {
        if (headDepartments == null | headDepartments.size() == 0) {
            return null;
        }
        Set<Department> subDepartments = new HashSet<>();
        for (Department department : headDepartments) {
            subDepartments.add(department);
            getTreeOfDepartments(department.getSubDepartments());
        }
        headDepartments.addAll(subDepartments);
        return headDepartments;
    }

    @Override
    public List<DepartmentDto> getSubordinateDepartments(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        List<DepartmentDto> departmentsDtoList = new ArrayList<>();
        departmentRepository.findByHeadDepartmentId(id).
                forEach(department -> departmentsDtoList.add(enrichDepartmentDto(department)));
        return departmentsDtoList;
    }

    @Override
    public BigDecimal getSumOfSalary(Long id) {
        departmentRepository.findById(id).orElseThrow();
        return employeeRepository.getSumOfSalaryInDepartment(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesOfDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        List<EmployeeDto> employeesDtoList = new ArrayList<>();
        employeeRepository.findByDepartmentId(id).
                forEach(employee -> employeesDtoList.add(mapperEmployee.employeeToDto(employee)));
        return employeesDtoList;
    }


    public DepartmentDto enrichDepartmentDto(Department department) {
        DepartmentDto departmentDto = mapperDepartment.departmentToDto(department);
        departmentDto.setBoss(mapperEmployee.employeeToDto(employeeRepository.getBoss(department.getId())));
        departmentDto.setCountEmployee(employeeRepository.getCountOfEmployeesInDepartment(department.getId()));
        return departmentDto;
    }

}
