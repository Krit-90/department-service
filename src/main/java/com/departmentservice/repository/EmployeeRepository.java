package com.departmentservice.repository;

import com.departmentservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "Select * From employees Where is_boss = true and department_id = ?", nativeQuery = true)
    Employee getBoss(Long id);

    @Query(value = "Select * From employees Where department_id = ?", nativeQuery = true)
    List<Employee> getEmployeesFromDepartment(Long departmentId);

    @Query(value = "Select count(id) From employees Where department_id = ?", nativeQuery = true)
    int getCountOfEmployeesInDepartment(Long departmentId);

    @Query(value = "Select sum(salary) From employees Where department_id = ?", nativeQuery = true)
    BigDecimal getSumOfSalaryInDepartment(Long departmentId);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update departments Set fired_date = ? Where id = ?", nativeQuery = true)
    void setFiredDate(LocalDate firedDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update employees Set department_id = ? Where id = ?", nativeQuery = true)
    void setDepartmentIdOfEmployee(Long departmentId, Long employeeId);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update employees Set department_id = ? Where department_id = ?", nativeQuery = true)
    void setDepartmentIdOfAllEmployeesFromSameDepartment(Long oldDepartmentId, Long newDepartmentId);

    @Query(value = "Select * From employees Where is_boss = true and department_id = " +
            "(Select department_id From employees Where id = ?)", nativeQuery = true)
    Employee getBossOfEmployee(Long id);

    List<Employee> findByLastNameAndFirstName(String lastName, String firstName);
}
