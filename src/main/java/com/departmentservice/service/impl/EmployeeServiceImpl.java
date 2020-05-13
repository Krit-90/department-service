package com.departmentservice.service.impl;

import com.departmentservice.dto.EmployeeDto;
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
import java.util.List;

@Transactional
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
        Employee employee = mapperEmployee.DtoToEmployee(employeeDto);
        employee.setEmploymentDate(LocalDate.now());
        employeeDto = mapperEmployee.employeeToDto(employeeRepository.save(employee));
        return employeeDto;
    }

    @Override
    public EmployeeDto updateEmployee(@NonNull Long id, @NonNull EmployeeDto employeeDto) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        Employee employee = employeeRepository.findById(id).get();
        if (employeeDto.getFirstName() != null) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getLastName() != null) {
            employee.setLastName(employeeDto.getLastName());
        }
        if (employeeDto.getPatronymic() != null) {
            employee.setPatronymic(employeeDto.getPatronymic());
        }
        if (employeeDto.getEmail() != null) {
            employee.setEmail(employeeDto.getEmail());
        }
        if (employeeDto.getBirthDate() != null) {
            if (employee.getEmploymentDate().isBefore(employee.getBirthDate())) {
                throw new ValidationException("Дата приема на работу, должна бы ть после дня рождения");
            }
            employee.setBirthDate(employeeDto.getBirthDate());
        }
        if (employeeDto.getPhone() != null) {
            employee.setPhone(employeeDto.getPhone());
        }
        if (employeeRepository.countBossOfDepartment(id) > 0) {
            throw new ValidationException("Может быть лишь один руководитель");
        } else {
            employee.setBoss(employeeDto.getBoss());
        }
        if (employeeDto.getJobTitle() != null) {
            employee.setJobTitle(employeeDto.getJobTitle());
        }
        if (employeeDto.getSalary() != null) {
            if (!employee.getBoss() & employeeDto.getSalary().compareTo(
                    employeeRepository.getBossOfEmployee(id).getSalary()) > 0) {
                throw new ValidationException("Зарплата не может быть больше, чем у руководителя");
            }
            employee.setSalary(employeeDto.getSalary());
        }
        if (employeeDto.getGender() != null) {
            employee.setGender(employeeDto.getGender());
        }
        if (employeeDto.getEmploymentDate() != null) {
            if (employee.getEmploymentDate().isBefore(employee.getBirthDate())) {
                throw new ValidationException("Дата приема на работу, должна быть после дня рождения");
            }
            employee.setEmploymentDate(employeeDto.getEmploymentDate());
        }
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
    public Employee firedEmployee(@NonNull Long id, @NonNull LocalDate firedDate) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        if (employee.getEmploymentDate().isAfter(firedDate)) {
            throw new ValidationException("Дата увольнения, должна быть после приема на работу");
        }
        employee.setFiredDate(firedDate);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeInfoById(@NonNull Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        return mapperEmployee.employeeToDto(employeeRepository.findById(id).get());
    }

    @Override
    public Employee changeDepartmentOfEmployee(@NonNull Long employeeId, @NonNull Long newDepartmentId) {
        employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        employeeRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        employeeRepository.setDepartmentIdOfEmployee(newDepartmentId, employeeId);
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public void changeDepartmentOfAllEmployeeFromSame(@NonNull Long oldDepartmentId, @NonNull Long newDepartmentId) {
        departmentRepository.findById(oldDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        employeeRepository.setDepartmentIdOfAllEmployeesFromSameDepartment(oldDepartmentId, newDepartmentId);
    }

    @Override
    public EmployeeDto getBossOfEmployee(@NonNull Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        return mapperEmployee.employeeToDto(employeeRepository.getBossOfEmployee(id));
    }

    @Override
    public List<Employee> getEmployeesByLastNameAndFirstName(@NonNull String lastName, @NonNull String firstName) {
        return employeeRepository.findByLastNameAndFirstName(lastName, firstName);
    }
}
