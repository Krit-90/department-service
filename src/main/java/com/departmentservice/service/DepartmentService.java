package com.departmentservice.service;

import com.departmentservice.dto.DepartmentDto;
import com.departmentservice.dto.DepartmentDtoReceive;
import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Department;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface DepartmentService {
    /**
     * Добавление департамента, при создании необходимо указывать, в какой департамент входит,
     * исключение - главенствующий департамент
     *
     * @param departmentDto Дто добавляемого департамент с собственным названием и названием вышестоящего
     * @return Дто добавленного департамента
     */
    DepartmentDtoReceive addDepartment(@NotNull(message = "Данные о департаменте не найдены")
            DepartmentDtoReceive departmentDto);

    /**
     * Обновление названия департамента
     *
     * @param newTitle Старое название
     * @param id       Id искомого департамента
     * @return Измененный департамент
     */
    Department updateDepartmentTitle(String newTitle, @NotNull(message = "Id не найден") Long id);

    /**
     * Удаление департамента
     *
     * @param id Id искомого департамента
     */
    void removeDepartment(@NotNull(message = "Id не найден") Long id);

    /**
     * Получение информации о департаменте
     *
     * @param id Id искомого департамента
     * @return Дто департамента, содержащий иформацию
     */
    DepartmentDto getDepartmentInfoById(@NotNull(message = "Id не найден") Long id);

    /**
     * Получение информации о департаментах, находящихся в непосредственном подчинении искомого департамента
     *
     * @param id Id искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getSubordinateDepartments(@NotNull(message = "Id не найден") Long id);

    /**
     * Получение информации о ВСЕХ департаментах, находящихся в подчинении искомого департамента
     *
     * @param id Id искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getAllSubordinateDepartments(@NotNull(message = "Id не найден") Long id);

    /**
     * Смена вышестоящего департамента
     *
     * @param idNewHead Id нового департамента
     * @param idCurrent Id искомого департамента
     * @return Измененный департамент
     */

    Department changeHeadDepartment(@NotNull(message = "Id не найден") Long idNewHead,
                                    @NotNull(message = "Id не найден") Long idCurrent);

    /**
     * Получение информации о всех вышестоящих департаментах
     *
     * @param id Id искомого департамента
     * @return Список дто департаментов
     */
    List<DepartmentDto> getAllHigherDepartments(@NotNull(message = "Id не найден") Long id);

    /**
     * Получение департамента по названию
     *
     * @param title Название искомого департамента
     * @return Искомый департамент
     */
    Department getDepartmentByTitle(String title);

    /**
     * Получение суммы зарплат работников искомого департамента
     *
     * @param id Id искомого департамента
     * @return Сумма зарплат
     */
    BigDecimal getSumOfSalary(@NotNull(message = "Id не найден") Long id);

    /**
     * Получение списка работников искомого департамента
     *
     * @param id Id искомого департамента
     * @return Список работников
     */
    List<EmployeeDto> getEmployeesOfDepartment(@NotNull(message = "Id не найден") Long id);

}
