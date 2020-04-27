package com.departmentservice.service;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.entity.Department;
import com.departmentservice.entity.Employee;

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
     * @param title Название искомого департамента
     * @return Дто департамента, содержащий иформацию
     */
    DepartmentDto getDepartmentInfoByTitle(String title);

    /**
     * Получение информации о департаментах, находящихся в непосредственном подчинении искомого департамента
     *
     * @param title Название искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getSubordinateDepartments(String title);

    /**
     * Получение информации о ВСЕХ департаментах, находящихся в подчинении искомого департамента
     *
     * @param title Название искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getAllSubordinateDepartments(String title);

    /**
     * Смена вышестоящего департамента
     * @param current Название искомого департамента
     * @param newHead Название нового департамента
     */

    void changeHeadDepartment(String current, String newHead);

    /**
     * Получение информации о всех вышестоящих департаментах
     * @param title Название искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getAllHigherDepartments(String title);

    /**
     * Получение суммы зарплат работников искомого департамента
     * @param title Название искомого департамента
     * @return Сумма зарплат
     */
    Integer sumOfSalary(String title);

    /**
     * Получение списка работников искомого департамента
     * @param title Название искомого департамента
     * @return Список работников
     */
    List<Employee> employeesOfDepartment(String title);
}
