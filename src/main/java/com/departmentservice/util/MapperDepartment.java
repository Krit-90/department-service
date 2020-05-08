package com.departmentservice.util;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperDepartment {
    DepartmentDto departmentToDto(Department department);
    Department DtoToDepartment(DepartmentDto departmentDto);
    @Mapping(source = "department.headDepartment.id", target = "headId")
    DepartmentDtoReceive departmentToDtoReceive(Department department);
    Department DtoReceiveToDepartment(DepartmentDtoReceive departmentDtoReceive);
}