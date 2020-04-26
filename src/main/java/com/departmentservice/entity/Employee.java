package com.departmentservice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Employees")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String lastname;
    @Column
    private String firsname;
    @Column
    private String patronymic;
    @Column
    private Boolean isMale;
    @Temporal(TemporalType.DATE)
    @Column(name = "Birth_Date")
    private Date birthDate;
    @Column
    private String phone;
    @Column
    private String email;
    @Column(name = "Employment_Date")
    private Date employmentDate;
    @Column(name = "Fired_Date")
    private Date firedDate;
    @Column
    private JobTitlesEnum jobTitle;
    @Column
    private Integer salary;
    @Column
    private Boolean isBoss;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }

    public Employee(String lastname, String firsname, String patronymic, Boolean isMale) {
        this.lastname = lastname;
        this.firsname = firsname;
        this.patronymic = patronymic;
        this.isMale = isMale;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirsname() {
        return firsname;
    }

    public void setFirsname(String firsname) {
        this.firsname = firsname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Date getFiredDate() {
        return firedDate;
    }

    public void setFiredDate(Date firedDate) {
        this.firedDate = firedDate;
    }

    public JobTitlesEnum getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitlesEnum jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (getLastname() != null ? !getLastname().equals(employee.getLastname()) : employee.getLastname() != null)
            return false;
        if (getFirsname() != null ? !getFirsname().equals(employee.getFirsname()) : employee.getFirsname() != null)
            return false;
        if (getPatronymic() != null ? !getPatronymic().equals(employee.getPatronymic()) : employee.getPatronymic() != null)
            return false;
        if (isMale != null ? !isMale.equals(employee.isMale) : employee.isMale != null) return false;
        if (getBirthDate() != null ? !getBirthDate().equals(employee.getBirthDate()) : employee.getBirthDate() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(employee.getPhone()) : employee.getPhone() != null) return false;
        if (getEmail() != null ? !getEmail().equals(employee.getEmail()) : employee.getEmail() != null) return false;
        if (getEmploymentDate() != null ? !getEmploymentDate().equals(employee.getEmploymentDate()) : employee.getEmploymentDate() != null)
            return false;
        if (getFiredDate() != null ? !getFiredDate().equals(employee.getFiredDate()) : employee.getFiredDate() != null)
            return false;
        if (getJobTitle() != employee.getJobTitle()) return false;
        if (getSalary() != null ? !getSalary().equals(employee.getSalary()) : employee.getSalary() != null)
            return false;
        if (isBoss != null ? !isBoss.equals(employee.isBoss) : employee.isBoss != null) return false;
        return getDepartment() != null ? getDepartment().equals(employee.getDepartment()) : employee.getDepartment() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getFirsname() != null ? getFirsname().hashCode() : 0);
        result = 31 * result + (getPatronymic() != null ? getPatronymic().hashCode() : 0);
        result = 31 * result + (isMale != null ? isMale.hashCode() : 0);
        result = 31 * result + (getBirthDate() != null ? getBirthDate().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getEmploymentDate() != null ? getEmploymentDate().hashCode() : 0);
        result = 31 * result + (getFiredDate() != null ? getFiredDate().hashCode() : 0);
        result = 31 * result + (getJobTitle() != null ? getJobTitle().hashCode() : 0);
        result = 31 * result + (getSalary() != null ? getSalary().hashCode() : 0);
        result = 31 * result + (isBoss != null ? isBoss.hashCode() : 0);
        result = 31 * result + (getDepartment() != null ? getDepartment().hashCode() : 0);
        return result;
    }
}
