package com.departmentservice.repository;

import com.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByTitle(String title);
    Optional<Department> findById(Long id);

    Set<Department> findByHeadDepartmentId(Long headDepartmentId);

    int countByHeadDepartmentIsNull();

}
