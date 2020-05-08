package com.departmentservice.repository;

import com.departmentservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByLastNameAndFirstName(String lastName, String firstName);

    @Query(value = "Select Sum(e.salary) From Employee e Where e.department.id = :departmentId")
    BigDecimal sumSalaryInDepartment(@RequestParam("id") Long departmentId);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Employee e Set e.department.id = :departmentId Where e.id = :employeeId")
    void setDepartmentIdOfEmployee(@RequestParam("departmentId") Long departmentId,
                                   @RequestParam("employeeId") Long employeeId);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Employee e Set e.department.id = :newDepartmentId Where e.department.id = :oldDepartmentId")
    void setDepartmentIdOfAllEmployeesFromSameDepartment(@RequestParam("oldDepartmentId") Long oldDepartmentId,
                                                         @RequestParam("newDepartmentId") Long newDepartmentId);

    @Query(value = "Select b From Employee b Where b.isBoss = true and b.department.id = " +
            "(Select e.department.id From Employee e Where e.id = :id)")
    Employee getBossOfEmployee(@RequestParam("id") Long id);

    @Query(value = "Select count(b.id) From Employee b Where b.isBoss = true and b.department.id = " +
            "(Select e.department.id From Employee e Where e.id = :id)")
    int countBossOfDepartment(@RequestParam("id") Long id);
}
