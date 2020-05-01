package com.departmentservice.dto;

import com.departmentservice.entity.JobTitles;
import com.departmentservice.entity.Sex;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDto {
    private String lastName;
    private String firstName;
    private String patronymic;
    private Sex gender;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private LocalDate employmentDate;
    private LocalDate firedDate;
    private JobTitles jobTitle;
    private BigDecimal salary;
    private Boolean isBoss;
    private DepartmentDto department;

    public EmployeeDto() {
    }

    public EmployeeDto(String lastName, String firstName, String patronymic, Sex gender, LocalDate birthDate,
                       String phone, String email, LocalDate employmentDate, LocalDate firedDate, JobTitles jobTitle,
                       BigDecimal salary, Boolean isBoss, DepartmentDto department) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.employmentDate = employmentDate;
        this.firedDate = firedDate;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.isBoss = isBoss;
        this.department = department;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public LocalDate getFiredDate() {
        return firedDate;
    }

    public void setFiredDate(LocalDate firedDate) {
        this.firedDate = firedDate;
    }

    public JobTitles getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitles jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmployeeDto{");
        sb.append("lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", patronymic='").append(patronymic).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", employmentDate=").append(employmentDate);
        sb.append(", firedDate=").append(firedDate);
        sb.append(", jobTitle=").append(jobTitle);
        sb.append(", salary=").append(salary);
        sb.append(", isBoss=").append(isBoss);
        sb.append(", department=").append(department);
        sb.append('}');
        return sb.toString();
    }
}
