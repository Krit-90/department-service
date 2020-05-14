package com.departmentservice.service.impl;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import com.departmentservice.exception.NoSuchElementInDBException;
import com.departmentservice.exception.ValidationException;
import com.departmentservice.repository.DepartmentRepository;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.DepartmentService;
import com.departmentservice.util.MapperDepartment;
import com.departmentservice.util.MapperEmployee;
import lombok.NonNull;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    public DepartmentDtoReceive addDepartment(@NonNull DepartmentDtoReceive departmentDtoReceive) {
        Department headDepartment = null;
        if (departmentDtoReceive.getHeadId() == null && departmentRepository.countByHeadDepartmentIsNull() > 0) {
            throw new NoSuchElementInDBException("Самый верхний депаратмент уже существует");
        } else {
            if (departmentDtoReceive.getHeadId() != null) {
                headDepartment = departmentRepository.findById(departmentDtoReceive.getHeadId())
                        .orElseThrow(() -> new NoSuchElementInDBException("Вышестоящий департамент не найден"));
            }
        }
        Department department = mapperDepartment.DtoReceiveToDepartment(departmentDtoReceive);
        department.setHeadDepartment(headDepartment);
        departmentRepository.save(department);
        return mapperDepartment.departmentToDtoReceive(department);
    }

    @Override
    public DepartmentDto updateDepartmentTitle(String newTitle, @NonNull Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        department.setTitle(newTitle);
        return mapperDepartment.departmentToDto(departmentRepository.save(department));
    }

    @Override
    public void removeDepartment(@NonNull Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            return;
        }
        if (!department.getEmployees().isEmpty()) {
            throw new ValidationException("Невозможно удаление департамента при наличии руботников");
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDto getDepartmentInfoById(@NonNull Long id) {
        return enrichDepartmentDto(departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден")));
    }

    @Override
    public DepartmentDto changeHeadDepartment(@NonNull Long idNewHead, @NonNull Long idCurrent) {
        if (idCurrent.equals(idNewHead)) {
            throw new ValidationException("Департамент не может быть главныи для самого себя");
        }
        Department department = departmentRepository.findById(idCurrent).orElseThrow(() ->
                new NoSuchElementInDBException("Департамент не найден"));
        Department headDepartment = departmentRepository.findById(idNewHead).orElseThrow(() ->
                new NoSuchElementInDBException("Департамент не найден"));
        department.setHeadDepartment(headDepartment);
        return mapperDepartment.departmentToDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto getDepartmentByTitle(@NonNull String title) {
        return mapperDepartment.departmentToDto(departmentRepository.findByTitle(title).
                orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден")));
    }

    @Override
    public List<DepartmentDto> getAllHigherDepartments(@NonNull Long id) {
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

    @Override
    public List<DepartmentDto> getAllSubordinateDepartments(@NonNull Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Дапартамент не найден"));
        List<Department> subDepartments = new ArrayList<>();
        DepartmentIterator departmentIterator = new DepartmentIterator(department);
        while (departmentIterator.hasNext()) {
            subDepartments.add(departmentIterator.next());
        }
        List<DepartmentDto> subDepartmentsDto = new ArrayList<>();
        subDepartments.forEach(dep -> subDepartmentsDto.add(enrichDepartmentDto(dep)));
        return subDepartmentsDto;
    }

    @Override
    public List<DepartmentDto> getSubordinateDepartments(@NonNull Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        List<DepartmentDto> departmentsDtoList = new ArrayList<>();
        department.getSubDepartment().forEach(dep -> departmentsDtoList.add(enrichDepartmentDto(dep)));
        return departmentsDtoList;
    }

    //    TODO Решил вообще без employeeRepository обойтись. Почему то через лямбду не хочет
    @Override
    public BigDecimal getSumOfSalary(@NonNull Long id) {
        BigDecimal sumOfSalary = new BigDecimal(0);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
//        department.getEmployees().forEach(employee -> sumOfSalary = sumOfSalary.add(employee.getSalary());
        Set<Employee> employees = department.getEmployees();
        for (Employee employee : employees) {
            sumOfSalary = sumOfSalary.add(employee.getSalary());
        }
        return sumOfSalary;
    }

    @Override
    public List<EmployeeDto> getEmployeesOfDepartment(@NonNull Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        List<EmployeeDto> employeesDtoList = new ArrayList<>();
        department.getEmployees().forEach(employee -> employeesDtoList.add(mapperEmployee.employeeToDto(employee)));
        return employeesDtoList;
    }


    private DepartmentDto enrichDepartmentDto(Department department) {
        DepartmentDto departmentDto = mapperDepartment.departmentToDto(department);
        departmentDto.setBoss(mapperEmployee.employeeToDto(employeeRepository
                .findByDepartmentIdAndIsBossTrue(department.getId())));
        departmentDto.setCountEmployee(employeeRepository.countIdByDepartmentId(department.getId()));
        return departmentDto;
    }


    private static class DepartmentIterator implements Iterator<Department> {
        Department nextDepartment;
        Queue<Department> queue = new LinkedList<>();

        public DepartmentIterator(Department department) {
            queue.addAll(department.getSubDepartment());
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Department next() {
            nextDepartment = queue.poll();
            assert nextDepartment != null;
            if (nextDepartment.getSubDepartment() != null) {
                queue.addAll(nextDepartment.getSubDepartment());
            }
            return nextDepartment;
        }
    }
}
