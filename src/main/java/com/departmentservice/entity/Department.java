package com.departmentservice.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "Departments")
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Temporal(TemporalType.DATE)
    @Column
    private Date creationDate;
    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;
    // TODO: нужно ли связывать аннотацией с базей?

    private Department headDepartment;

    public Department() {
    }

    public Department(String title) {
        this.title = title;
    }



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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Department getHeadDepartment() {
        return headDepartment;
    }

    public void setHeadDepartment(Department headDepartment) {
        this.headDepartment = headDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        if (!id.equals(that.id)) return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getCreationDate() != null ? !getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() != null)
            return false;
        if (getEmployees() != null ? !getEmployees().equals(that.getEmployees()) : that.getEmployees() != null)
            return false;
        return getHeadDepartment() != null ? getHeadDepartment().equals(that.getHeadDepartment()) : that.getHeadDepartment() == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getEmployees() != null ? getEmployees().hashCode() : 0);
        result = 31 * result + (getHeadDepartment() != null ? getHeadDepartment().hashCode() : 0);
        return result;
    }
}
