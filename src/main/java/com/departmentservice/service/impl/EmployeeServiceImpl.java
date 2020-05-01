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
        if (isPresent(id)) {
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
            employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public void removeEmployee(Long id) {
        if (isPresent(id)) {
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public Employee firedEmployee(Long id, LocalDate firedDate) {
        if (isPresent(id)) {
            employeeRepository.setFiredDate(firedDate, id);
            return employeeRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public EmployeeDto getEmployeeInfoById(Long id) {
        if (isPresent(id)) {
            return mapperEntity.employeeToDto(employeeRepository.findById(id).get());
        }
        return null;
    }

    @Override
    public Employee changeDepartmentOfEmployee(Long employeeId, Long newDepartmentId) {
        if (isPresent(employeeId) & isPresentDepartment(newDepartmentId)) {
            employeeRepository.setDepartmentIdOfEmployee(newDepartmentId, employeeId);
        }
        return null;
    }

    @Override
    public void changeDepartmentOfAllEmployeeFromSame(Long oldDepartmentId, Long newDepartmentId) {
        if (isPresentDepartment(oldDepartmentId) & isPresentDepartment(newDepartmentId)) {
            employeeRepository.setDepartmentIdOfAllEmployeesFromSameDepartment(oldDepartmentId, newDepartmentId);
        }
    }

    @Override
    public EmployeeDto getBossOfEmployee(Long id) {
        if (isPresent(id)) {
            return mapperEntity.employeeToDto(employeeRepository.getBossOfEmployee(id));
        }
        return null;
    }

    @Override
    public List<Employee> getEmployeesByLastNameAndFirstName(String lastName, String firstName) {
        return employeeRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    private boolean isPresentDepartment(Long id) {
        if (departmentRepository.findById(id).isPresent()) {
            return true;
        }
        throw new NoSuchElementException("Данный департамент не найден");

    }

    private boolean isPresent(Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            return true;
        }
        throw new NoSuchElementException("Данный работник не найден");
    }
}
