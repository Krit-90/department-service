package com.departmentservice.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "Employees")
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String patronymic;
    // TODO: Удобно ли булево использовать или enum?
    @Enumerated(EnumType.STRING)
    @Column
    private  Sex gender;
    @Temporal(TemporalType.DATE)
    @Column
    private Date birthDate;
    @Column
    private String phone;
    @Column
    private String email;
    // TODO: Нуэна ли генерация даты создания или устройства на работу
    @Column(name = "Employment_Date")
    private Date employmentDate;
    @Column(name = "Fired_Date")
    private Date firedDate;
    @Enumerated(EnumType.STRING)
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

    public Employee(String lastName, String firstName, String patronymic, Sex gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
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

        if (!id.equals(employee.id)) return false;
        if (getLastName() != null ? !getLastName().equals(employee.getLastName()) : employee.getLastName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(employee.getFirstName()) : employee.getFirstName() != null)
            return false;
        if (getPatronymic() != null ? !getPatronymic().equals(employee.getPatronymic()) : employee.getPatronymic() != null)
            return false;
        if (getGender() != employee.getGender()) return false;
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
        int result = id.hashCode();
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getPatronymic() != null ? getPatronymic().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
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
