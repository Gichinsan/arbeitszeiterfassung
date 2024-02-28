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

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.model.Worktype;
import de.gichinsan.arbeitszeiterfassung.service.EmployeeService;
import de.gichinsan.arbeitszeiterfassung.service.WorkHoursService;
import de.gichinsan.arbeitszeiterfassung.service.WorktypeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Component(value = "workhours")
@RestController
@RequestScoped
@Slf4j
public class WorkhoursController implements Serializable {

    public static final int PAUSE_DEF_VALUE = 40;

    private LocalDate arbeitstag;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private LocalTime minTime;
    private LocalTime maxTime;
    private LocalTime pause;

    private String berechnung;

    private List<Worktype> worktypeList;
    private Integer worktype;

    @Autowired
    private WorkHoursService service;

    @Autowired
    private WorktypeService worktypeService;

    @Autowired
    private EmployeeService employeeService;

    @PostConstruct
    public void init() {

        int pauseDefValue;

        log.error("Get Employees --> " + employeeService.getAllEmployee().size());

        if (!employeeService.getAllEmployee().isEmpty()) {
            pauseDefValue = employeeService.getAllEmployee().get(0).getPausemgmt();
            log.error("Get Employees Pause --> " + pauseDefValue);

        } else {
            pauseDefValue = PAUSE_DEF_VALUE;
        }

        minTime = LocalTime.of(6, 0);
        maxTime = LocalTime.of(19, 0);
        setPause(LocalTime.of(0, pauseDefValue));
    }

    /**
     *
     * @return List of Worktypes
     */
    public List<Worktype> getWorktypeList() {
        return worktypeService.getAllWorktypes();
    }

    /**
     * homepage
     *
     * @return String to "index"
     */
    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    /**
     * saveAction
     *
     * @return "/overview" in case of success and return to "/index" in case of action needed.
     */
    public String saveAction() {

        Duration duration = Duration.between(endZeit, startZeit);
        long diff = Math.abs(duration.toMinutes());

        Duration mduration = Duration.ofMinutes(pause.getMinute());
        Duration hduration = Duration.ofHours(pause.getHour());

        String realDiff = LocalTime.MIN.plus(
                Duration.ofMinutes(diff).minus(mduration).minus(hduration)
        ).toString();

        setBerechnung(realDiff);

        Workhours wh = new Workhours();
        log.info(employeeService.getAllEmployee().get(0).toString());
        wh.setEmployee_id(employeeService.getAllEmployee().get(0).getId());
        wh.setDate(arbeitstag);
        wh.setWorktype(worktype);
        wh.setMonth(arbeitstag.getMonthValue());
        wh.setStartTimeHours(startZeit.getHour());
        wh.setStartTimeMinutes(startZeit.getMinute());
        wh.setStopTimeHours(endZeit.getHour());
        wh.setStopTimeMinutes(endZeit.getMinute());
        wh.setDurationPauseHours(pause.getHour());
        wh.setDurationPauseMinutes(pause.getMinute());
        wh.setWorkingHours(realDiff);
        if (service.save(wh)) {
            addMessage("Arbeitstag erfolgreich gespeichert");
            setArbeitstag(null);
            setStartZeit(null);
            setEndZeit(null);
            setPause(LocalTime.of(0, employeeService.getAllEmployee().get(0).getPausemgmt()));
            setBerechnung(null);
            return "/overview";
        } else {
            addErrorMessage("Für diesen Tag exisiteren schon einträge! " + arbeitstag);
            return "/index";
        }
    }

    /**
     * addMessage
     *
     * @param summary String
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * addErrorMessage
     *
     * @param summary String
     */
    public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
