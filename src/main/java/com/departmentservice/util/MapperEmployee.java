package com.departmentservice.util;

import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperEmployee {
    @Mapping(source = "department.title", target = "departmentTitle")
    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto employeeToDto(Employee employee);
    Employee DtoToEmployee(EmployeeDto employee);
}