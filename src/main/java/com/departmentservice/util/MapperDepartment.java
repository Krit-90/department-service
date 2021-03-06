package com.departmentservice.util;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = LocalDate.class)
public interface MapperDepartment {
    DepartmentDto departmentToDto(Department department);
    Department DtoToDepartment(DepartmentDto departmentDto);
    @Mapping(source = "department.headDepartment.id", target = "headId")
    DepartmentDtoReceive departmentToDtoReceive(Department department);
    @Mapping(target = "creationDate", expression = "java(LocalDate.now())")
    Department DtoReceiveToDepartment(DepartmentDtoReceive departmentDtoReceive);
}