package com.departmentservice.dto;

import com.departmentservice.entity.Employee;

import java.util.Date;

public class DepartmentDto {
    private String title;
    private Date creationDate;
    private Employee boss;
    private Integer countEmployee;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public Integer getCountEmployee() {
        return countEmployee;
    }

    public void setCountEmployee(Integer countEmployee) {
        this.countEmployee = countEmployee;
    }
}
