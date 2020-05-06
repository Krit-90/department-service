package com.departmentservice.repository;

import com.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByTitle(String title);

    Set<Department> findByHeadDepartmentId(Long headDepartmentId);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update departments Set head_department_id = ? Where id = ?", nativeQuery = true)
    void changeHeadDepartment(Long idCurrent, Long idNewHead);

    @Query(value = "Select count(id) From departments Where head_department_id is null",
            nativeQuery = true)
    int getCountOfDepartmentsWhereHeadIsNull();
}
