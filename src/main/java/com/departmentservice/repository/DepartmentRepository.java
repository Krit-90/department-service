package com.departmentservice.repository;

import com.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByTitle(String title);

    List<Department> findByHeadDepartmentId(Long headDepartmentId);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update departments Set head_department_id = ? Where id = ?", nativeQuery = true)
    void changeHeadDepartment(Long idCurrent, Long idNewHead);

}
