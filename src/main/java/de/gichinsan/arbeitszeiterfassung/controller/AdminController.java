/**
 * This file is part of arbeitszeiterfassung.
 * <p>
 * arbeitszeiterfassung is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * arbeitszeiterfassung is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * arbeitszeiterfassung. If not, see <http://www.gnu.org/licenses/>.
 */
package de.gichinsan.arbeitszeiterfassung.controller;

import de.gichinsan.arbeitszeiterfassung.model.Employee;
import de.gichinsan.arbeitszeiterfassung.service.EmployeeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Data
@Component(value = "adminctl")
@RestController
@RequestScoped
@Slf4j
public class AdminController implements Serializable {

    private String firstName;
    private String lastName;
    private String weeklyWorkingshours;
    private String maxDailyWorkinghours;

    @Autowired
    private EmployeeService service;


    @PostConstruct
    public void init() {
        showValues();
    }

    /**
     * @return
     */
    public String showValues() {
        try {
            List<Employee> employeeList = service.getAllEmployee();
            setFirstName(employeeList.get(0).getFirstName());
            setLastName(employeeList.get(0).getLastName());
            setWeeklyWorkingshours(String.valueOf(employeeList.get(0).getWeeklyWorkinghours()));
            setMaxDailyWorkinghours(String.valueOf(employeeList.get(0).getMaxDailyWorkinghours()));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return "/admin";
    }

    /**
     * @return
     */
    public String saveAction() {
        Employee em = new Employee();
        em.setFirstName(getFirstName());
        em.setLastName(getLastName());
        em.setWeeklyWorkinghours(Integer.parseInt(getWeeklyWorkingshours()));
        em.setMaxDailyWorkinghours(Integer.parseInt(getMaxDailyWorkinghours()));
        service.save(em);
        return "/admin";
    }

    /**
     * @param summary
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * @param summary
     */
    public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @RequestMapping(value = "/v1/employees", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployees() {
        return service.getAllEmployee();
    }
}
