package com.departmentservice.service.impl;

import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Employee;
import com.departmentservice.repository.DepartmentRepository;
import com.departmentservice.repository.EmployeeRepository;
import com.departmentservice.service.EmployeeService;
import com.departmentservice.util.MapperEntity;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MapperEntity mapperEntity;
    private final DepartmentRepository departmentRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, MapperEntity mapperEntity,
                               DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.mapperEntity = Mappers.getMapper(MapperEntity.class);
        this.departmentRepository = departmentRepository;
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmploymentDate(LocalDate.now());
        return employeeRepository.save(employee);
    }

    // TODO: Получилось что то монструозное, думал и искал, но не понял как сделать это проще
    @Override
    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Работник не найден"));
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
            employee.setEmploymentDate(employeeDto.getEmploymentDate());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void removeEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Работник не найден"));
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee firedEmployee(Long id, LocalDate firedDate) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Работник не найден"));
        employee.setFiredDate(firedDate);
        return employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeInfoById(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Работник не найден"));
        return mapperEntity.employeeToDto(employeeRepository.findById(id).get());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Employee changeDepartmentOfEmployee(Long employeeId, Long newDepartmentId) {
        employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Работник не найден"));
        employeeRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementException("Работник не найден"));
        employeeRepository.setDepartmentIdOfEmployee(newDepartmentId, employeeId);
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public void changeDepartmentOfAllEmployeeFromSame(Long oldDepartmentId, Long newDepartmentId) {
        departmentRepository.findById(oldDepartmentId)
                .orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
        departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new NoSuchElementException("Департамент не найден"));
            employeeRepository.setDepartmentIdOfAllEmployeesFromSameDepartment(oldDepartmentId, newDepartmentId);
    }

    @Override
    public EmployeeDto getBossOfEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Работник не найден"));
        return mapperEntity.employeeToDto(employeeRepository.getBossOfEmployee(id));
    }

    @Override
    public List<Employee> getEmployeesByLastNameAndFirstName(String lastName, String firstName) {
        return employeeRepository.findByLastNameAndFirstName(lastName, firstName);
    }
}
