package com.departmentservice.util;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperDepartment {
    DepartmentDto departmentToDto(Department department);
    Department DtoToDepartment(DepartmentDto departmentDto);
}