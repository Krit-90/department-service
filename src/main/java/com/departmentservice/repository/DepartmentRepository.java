package com.departmentservice.repository;

import com.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findDepartmentByTitle(String title);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update departments Set title = ? Where id = ?", nativeQuery = true)
    void updateTitle(String after, Long before);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update departments Set head_department_id = ? Where id = ?", nativeQuery = true)
    void changeHeadDepartment(Long idCurrent, Long idNewHead);

    @Query(value = "Select * From departments Where head_department_id = ?", nativeQuery = true)
    List<Department> getSubDepartments(Long headDepartmentId);

}
