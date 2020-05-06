package com.departmentservice.service.impl;

import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Employee;
import com.departmentservice.exception.NoSuchElementInDBException;
import com.departmentservice.exception.ValidationException;
import com.departmentservice.repository.DepartmentRepository;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.EmployeeService;
import com.departmentservice.util.MapperEmployee;
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

    public Employee addEmployee(Employee employee) {
        employee.setEmploymentDate(LocalDate.now());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
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
        if (employeeDto.getSalary() != null) {
            employee.setSalary(employeeDto.getSalary());
        }
        if (employeeDto.getJobTitle() != null) {
            employee.setJobTitle(employeeDto.getJobTitle());
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
        return employeeRepository.save(employee);
    }

    @Override
    public void removeEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee firedEmployee(Long id, LocalDate firedDate) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        if (employee.getEmploymentDate().isAfter(firedDate)) {
            throw new ValidationException("Дата увольнения, должна быть после приема на работу");
        }
        employee.setFiredDate(firedDate);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeInfoById(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        return mapperEmployee.employeeToDto(employeeRepository.findById(id).get());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Employee changeDepartmentOfEmployee(Long employeeId, Long newDepartmentId) {
        employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        employeeRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        employeeRepository.setDepartmentIdOfEmployee(newDepartmentId, employeeId);
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public void changeDepartmentOfAllEmployeeFromSame(Long oldDepartmentId, Long newDepartmentId) {
        departmentRepository.findById(oldDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
        departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementInDBException("Департамент не найден"));
            employeeRepository.setDepartmentIdOfAllEmployeesFromSameDepartment(oldDepartmentId, newDepartmentId);
    }

    @Override
    public EmployeeDto getBossOfEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementInDBException("Работник не найден"));
        return mapperEmployee.employeeToDto(employeeRepository.getBossOfEmployee(id));
    }

    @Override
    public List<Employee> getEmployeesByLastNameAndFirstName(String lastName, String firstName) {
        return employeeRepository.findByLastNameAndFirstName(lastName, firstName);
    }
}
