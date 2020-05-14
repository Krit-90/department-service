package com.departmentservice.service.impl;

import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import com.departmentservice.exception.NoSuchElementInDBException;
import com.departmentservice.exception.ValidationException;
import com.departmentservice.repository.DepartmentRepository;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.EmployeeService;
import com.departmentservice.util.MapperEmployee;
import lombok.NonNull;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MapperEmployee mapperEmployee;
    private final DepartmentRepository departmentRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, MapperEmployee mapperEmployee,
                               DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.mapperEmployee = Mappers.getMapper(MapperEmployee.class);
        this.departmentRepository = departmentRepository;
    }

    public EmployeeDto addEmployee(@NonNull EmployeeDto employeeDto) {
        validateOrThrow(employeeDto);
        Employee employee = mapperEmployee.DtoToEmployee(employeeDto);
        employee.setEmploymentDate(LocalDate.now());
        employeeDto = mapperEmployee.employeeToDto(employeeRepository.save(employee));
        return employeeDto;
    }

    @Override
    public EmployeeDto updateEmployee(@NonNull Long id, @NonNull EmployeeDto employeeDto) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        employeeDto.setId(id);
        validateOrThrow(employeeDto);
        Employee employee = mapperEmployee.DtoToEmployee(employeeDto);
        employeeDto = mapperEmployee.employeeToDto(employeeRepository.save(employee));
        return employeeDto;
    }
    @Override
    public void removeEmployee(@NonNull Long id) {
        if(employeeRepository.findById(id).orElse(null) == null) {
            return;
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto firedEmployee(@NonNull Long id, @NonNull LocalDate firedDate) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        if (employee.getEmploymentDate().isAfter(firedDate)) {
            throw new ValidationException("Дата увольнения, должна быть после приема на работу");
        }
        employee.setFiredDate(firedDate);
        employee.setDepartment(null);
        return mapperEmployee.employeeToDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto getEmployeeInfoById(@NonNull Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        return mapperEmployee.employeeToDto(employee);
    }

    @Transactional
    @Override
    public EmployeeDto changeDepartmentOfEmployee(@NonNull Long employeeId, @NonNull Long newDepartmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        Department department = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        employee.setDepartment(department);
        EmployeeDto employeeDto = mapperEmployee.employeeToDto(employee);
        validateOrThrow(employeeDto);
        employeeRepository.save(employee);
        return employeeDto;
    }
    @Override
    public void changeDepartmentOfAllEmployeeFromSame(@NonNull Long oldDepartmentId, @NonNull Long newDepartmentId) {
        Department oldDepartment = departmentRepository.findById(oldDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        oldDepartment.getEmployees().forEach(employee -> changeDepartmentOfEmployee(employee.getId(), newDepartmentId));
    }

    @Override
    public EmployeeDto getBossOfEmployee(@NonNull Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        return mapperEmployee.employeeToDto(employeeRepository.getBossOfDepartment(employee.getDepartment().getId()));
    }

    @Override
    public List<EmployeeDto> getEmployeesByLastNameAndFirstName(@NonNull String lastName, @NonNull String firstName) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeRepository.findByLastNameAndFirstName(lastName, firstName)
                .forEach(employee -> employeeDtos.add(mapperEmployee.employeeToDto(employee)));
        return employeeDtos;
    }

    //  TODO: У нас есть конкретный метод на увольнение, не стоит ли при создании/сохранении вообще не увольнять
    private void validateOrThrow(EmployeeDto employeeDto) {
        if (employeeDto.getFiredDate() !=null) {
            throw new ValidationException("При создании или обновлении нельзя увольнять");
        }
        if (employeeDto.getBoss() && employeeRepository.countBossOfDepartment(employeeDto.getDepartmentId()) > 0) {
            throw new ValidationException("Может быть лишь один руководитель");
        }
        if (!employeeDto.getBoss() & employeeDto.getSalary().compareTo(
                employeeRepository.getBossOfDepartment(employeeDto.getDepartmentId()).getSalary()) > 0) {
            throw new ValidationException("Зарплата не может быть больше, чем у руководителя");
        }
        if (employeeDto.getEmploymentDate().isBefore(employeeDto.getBirthDate())) {
            throw new ValidationException("Дата приема на работу, должна бы ть после дня рождения");
        }
        if (employeeDto.getEmploymentDate().isBefore(employeeDto.getBirthDate())) {
            throw new ValidationException("Дата приема на работу, должна быть после дня рождения");
        }
    }
}
