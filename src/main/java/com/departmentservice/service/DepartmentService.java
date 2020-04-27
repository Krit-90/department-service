package com.departmentservice.service;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface DepartmentService {
    /**
     * Добавление департамента, при создании необходисо указывать, в какой департамент входит,
     * исключение - главенствующий департамент
     *
     * @param department Добавляемый департамент
     * @param title Название вышестоящего департамента
     */
    void addDepartment(Department department, String title);

    /**
     * Обновление названия департамента
     *
     * @param after Старое название
     * @param before  Новое название
     */
    void updateDepartmentTitle(String after, String before);

    /**
     * Получение информации о департаменте
     *
     * @param id Id искомого департамента
     * @return Дто департамента, содержащий иформацию
     */
    DepartmentDto getDepartmentInfoById(Long id);

    /**
     * Получение информации о департаментах, находящихся в непосредственном подчинении искомого департамента
     *
     * @param id Id искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getSubordinateDepartments(Long id);

    /**
     * Получение информации о ВСЕХ департаментах, находящихся в подчинении искомого департамента
     *
     * @param id Id искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getAllSubordinateDepartments(Long id);

    /**
     * Смена вышестоящего департамента
     * @param idCurrent Id искомого департамента
     * @param idNewHead Id нового департамента
     */

    void changeHeadDepartment(Long idCurrent, Long idNewHead);

    /**
     * Получение информации о всех вышестоящих департаментах
     * @param id Id искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getAllHigherDepartments(Long id);

    /**
     * Получение суммы зарплат работников искомого департамента
     * @param id Id искомого департамента
     * @return Сумма зарплат
     */
    BigDecimal getSumOfSalary(Long id);

    /**
     * Получение списка работников искомого департамента
     * @param id Id искомого департамента
     * @return Список работников
     */
    List<Employee> getEmployeesOfDepartment(Long id);
}
