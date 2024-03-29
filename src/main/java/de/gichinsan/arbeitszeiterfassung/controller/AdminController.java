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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gichinsan.arbeitszeiterfassung.model.Employee;
import de.gichinsan.arbeitszeiterfassung.model.Role;
import de.gichinsan.arbeitszeiterfassung.model.Usertbl;
import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.service.EmployeeService;
import de.gichinsan.arbeitszeiterfassung.service.UsertblService;
import de.gichinsan.arbeitszeiterfassung.service.WorkHoursService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Component(value = "adminctl")
@RestController
@RequestScoped
@Transactional
@Slf4j
public class AdminController implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String userpwd;
    private String weeklyWorkingshours;
    private String maxDailyWorkinghours;
    private Employee em = new Employee();
    private String pausemgmt;

    @Autowired
    private EmployeeService service;

    @Autowired
    private UsertblService usertblService;

    @Autowired
    private WorkHoursService workHoursService;


    @PostConstruct
    public void init() {
        showValues();
    }

    /**
     * @return String /admin
     */
    public String showValues() {
        try {
            List<Employee> employeeList = service.getAllEmployee();
            setFirstName(employeeList.get(0).getFirstName());
            setLastName(employeeList.get(0).getLastName());
            setWeeklyWorkingshours(String.valueOf(employeeList.get(0).getWeeklyWorkinghours()));
            setMaxDailyWorkinghours(String.valueOf(employeeList.get(0).getMaxDailyWorkinghours()));
            setPausemgmt(String.valueOf(employeeList.get(0).getPausemgmt()));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return "/admin";
    }

    /**
     * @return String /admin
     */
    public String saveAction() {
        em.setFirstName(getFirstName());
        em.setLastName(getLastName());
        em.setWeeklyWorkinghours(Integer.parseInt(weeklyWorkingshours));
        em.setMaxDailyWorkinghours(Integer.parseInt(maxDailyWorkinghours));
        em.setPausemgmt(Integer.parseInt(pausemgmt));
        service.saveOrUpdate(em);

        Usertbl utbl = new Usertbl();
        Role role = new Role();

        utbl.setUsername(username);
        String generatedSecuredPasswordHash = BCrypt.hashpw(userpwd, BCrypt.gensalt(12));
        utbl.setPassword(generatedSecuredPasswordHash);

        role.setName("USER");
        Set<Role> set = Set.of(role);
        utbl.setRoles(set);
        utbl.setEnabled(true);
        usertblService.createOrUpdateUser(utbl);

        addMessage("Neuer User erstellt! " + username);
        return "/admin";
    }

    /**
     * @param summary as String
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * @param summary as String
     */
    public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * getEmployees<br>
     *
     * @return List AllEmployees
     */
    @RequestMapping(value = "/v1/employees", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployees() {
        return service.getAllEmployee();
    }

    /**
     * @param file as multipart/form-data
     * @return string /overview
     * @throws IOException If unable to save
     */
    @PostMapping(value = "/v1/uploadreport", consumes = "multipart/form-data")
    public String uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Workhours>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = file.getInputStream();
        try {
            List<Workhours> workhours = mapper.readValue(inputStream, typeReference);

            for (Workhours values : workhours) {
                workHoursService.save(values);
            }

            log.debug("Report Saved!");
        } catch (IOException e) {
            log.error("Unable to save report: " + e.getMessage());
        }
        return "/overview";
    }
}
