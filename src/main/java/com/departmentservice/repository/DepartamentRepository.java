package com.departmentservice.repository;

import com.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentRepository extends JpaRepository<Department, Long> {
    Department findDepartmentByTitle(String title);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Department Set title = ? Where title = ?")
    void updateTitle(String after, String before);
}
