package com.departmentservice.util;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperEntity {
    @Mapping(source = "department.title", target = "departmentTitle")
    EmployeeDto employeeToDto(Employee employee);
    Employee DtoToEmployee(EmployeeDto employee);
    DepartmentDto departmentToDto(Department department);
    Department dtoToDepartment(DepartmentDto department);
}
