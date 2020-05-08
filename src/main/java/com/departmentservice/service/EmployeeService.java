package com.departmentservice.service;

import com.departmentservice.dto.EmployeeDto;
import com.departmentservice.entity.Employee;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    /**
     * Добавление сотрудника
     *
     * @param employeeDto Данные о новом сотруднике
     * @return Дто добавленного сотрудника
     */
    EmployeeDto addEmployee(@NotNull(message = "Данные о работнике не найдены")EmployeeDto employeeDto);

    /**
     * Редактирование сведений о сотруднике департамента
     *
     * @param id          Id искомого сотрудника
     * @param employeeDto Дто сотрудника из которого берем сведения
     * @return Данные отредактированного сотрудника
     */
    EmployeeDto updateEmployee(@NotNull(message = "Id не найден") Long id,
                            @NotNull(message = "Данные о работнике не найдены") EmployeeDto employeeDto);

    /**
     * Удаление из базы
     *
     * @param id Id искомго сотрудкника
     */
    void removeEmployee(@NotNull(message = "Id не найден") Long id);

    /**
     * Увольнение сотрудника
     *
     * @param id        Id искомого сотрудника
     * @param firedDate Дата увольнения
     * @return Уволенный сотрудник
     */
    Employee firedEmployee(@NotNull(message = "Id не найден") Long id,
                           @NotNull(message = "Дата не найдена") LocalDate firedDate);

    /**
     * Получение информации о сотруднике
     *
     * @param id Id искомого сотрудника
     * @return Дто искомого сотрудника
     */
    EmployeeDto getEmployeeInfoById(@NotNull(message = "Id не найден") Long id);

    /**
     * Перевод сотрудника из одного департамента в другой
     *
     * @param employeeId      Id искомого сотрудника
     * @param newDepartmentId Id нового департамента
     * @return Переведенный работник
     */
    Employee changeDepartmentOfEmployee(@NotNull(message = "Id не найден") Long employeeId,
                                        @NotNull(message = "Id не найден") Long newDepartmentId);

    /**
     * Перевод всех сотрудников департамента в другой департамент
     *
     * @param oldDepartmentId Id текущего департамент
     * @param newDepartmentId Id департамента, в который хотим перевести
     */
    void changeDepartmentOfAllEmployeeFromSame(@NotNull(message = "Id не найден") Long oldDepartmentId,
                                               @NotNull(message = "Id не найден") Long newDepartmentId);

    /**
     * Получение информации о руководителе данного сотрудника
     *
     * @param id Id искомого сотрудника
     * @return Дто руководителя искомого сотрудника
     */
    EmployeeDto getBossOfEmployee(@NotNull(message = "Id не найден") Long id);

    /**
     * Получение списка сотрудников департамента по фамилии и имени
     *
     * @param lastName  Фамилия искомого сотрудника
     * @param firstName Имя искомого сотрудника
     * @return Список сотрудников с искомыми фамилией и именем
     */
    List<Employee> getEmployeesByLastNameAndFirstName(@NotNull(message = "Фамилия не найдено") String lastName,
                                                      @NotNull(message = "Имя не найдено") String firstName);

}
