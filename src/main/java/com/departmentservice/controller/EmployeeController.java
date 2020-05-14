package com.departmentservice.controller;

import com.departmentservice.dto.EmployeeDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("employees")
@RestController
public interface EmployeeController {
    @GetMapping("/{id}")
    /**
     * Get-запрос по получению информации о сотруднике
     *
     * @param id Id искомого сотрудника
     * @return Дто искомого сотрудника либо сообщение о неуспешном завершении метода
     */
    EmployeeDto getEmployee(@PathVariable(name = "id") Long id);

    @PostMapping("")
    /**
     * Post-запрос по добавлению сотрудника
     *
     * @param employeeDto Данные о новом сотруднике
     * @return Дто добавленного сотрудника либо сообщение о неуспешном завершении метода
     */
    EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto);

    @PostMapping("/{id}/update")
    /**
     * Post-запрос по редактированию сведений о сотруднике департамента
     *
     * @param id          Id искомого сотрудника
     * @param employeeDto Дто сотрудника из которого берем сведения
     * @return Данные отредактированного сотрудника либо сообщение о неуспешном завершении метода
     */
    EmployeeDto updateEmployee(@PathVariable(name = "id") Long id,
                               @RequestBody EmployeeDto employeeDto);

//    TODO: а в качестве чего добавлять LocalDate, RequestParam?
    @PutMapping("/{id}/fired")
    /**
     * Put-запрос по увольнению сотрудника
     *
     * @param id        Id искомого сотрудника
     * @param firedDate Дата увольнения
     * @return Уволенный сотрудник либо сообщение о неуспешном завершении метода
     */
    EmployeeDto firedEmployee(@PathVariable(name = "id") Long id, LocalDate firedDate);

    @PutMapping("/{id}/change-department")
    /**
     * Put-запрос по переводу сотрудника из одного департамента в другой
     *
     * @param employeeId      Id искомого сотрудника
     * @param newDepartmentId Id нового департамента
     * @return Дто переведенного работника либо сообщение о неуспешном завершении метода
     */
    EmployeeDto changeDepartmentOfEmployee(@PathVariable(name = "id") Long employeeId,
                                           @RequestParam("new-department-id") Long newDepartmentId);

    @PutMapping("/change-department-employees")
    /**
     * Put-запрос по переводу всех сотрудников департамента в другой департамент
     *
     * @param oldDepartmentId Id текущего департамент
     * @param newDepartmentId Id департамента, в который хотим перевести
     */
    void changeDepartmentOfAllEmployeeFromSame(@RequestParam("old-department-id") Long oldDepartmentId,
                                               @RequestParam("new-department-id") Long newDepartmentId);

    @GetMapping("/{id}/boss")
    /**
     * Get-запрос по получению информации о руководителе данного сотрудника
     *
     * @param id Id искомого сотрудника
     * @return Дто руководителя искомого сотрудника либо сообщение о неуспешном завершении метода
     */
    EmployeeDto getBossOfEmployee(@PathVariable(name = "id") Long id);

    @GetMapping("")
    /**
     * Get-запрос по получению списка сотрудников департамента по фамилии и имени
     *
     * @param lastName  Фамилия искомого сотрудника
     * @param firstName Имя искомого сотрудника
     * @return Список дто сотрудников с искомыми фамилией и именем либо сообщение о неуспешном завершении метода
     */
    List<EmployeeDto> getEmployeesByLastNameAndFirstName(@RequestParam("last-name") String lastName,
                                                      @RequestParam("first-name") String firstName);

}
