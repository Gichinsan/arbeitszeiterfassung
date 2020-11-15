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
import de.gichinsan.arbeitszeiterfassung.service.WorkHoursService;
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

@Data
@Component(value = "workhours")
@RestController
@RequestScoped
@Slf4j
public class WorkhoursController implements Serializable {

    private LocalDate arbeitstag;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private LocalTime minTime;
    private LocalTime maxTime;
    private LocalTime pause;
    private String berechnung;


    @Autowired
    private WorkHoursService service;

    @PostConstruct
    public void init() {
        minTime = LocalTime.of(6, 0);
        maxTime = LocalTime.of(19, 0);
    }


    /**
     * @return
     */
    @GetMapping("/")
    public String homepage() {
        return "index";
    }

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
        wh.setDate(arbeitstag);
        wh.setStartTimeHours(startZeit.getHour());
        wh.setStartTimeMinutes(startZeit.getMinute());
        wh.setStopTimeHours(endZeit.getHour());
        wh.setStopTimeMinutes(endZeit.getMinute());
        wh.setDurationPauseHours(pause.getHour());
        wh.setDurationPauseMinutes(pause.getMinute());
        wh.setWorkingHours(realDiff);
        service.save(wh);


        return "/index?faces-redirect=true";
    }

    public String startPause() {
        log.error(String.valueOf(System.currentTimeMillis()));
        return "";
    }

    public String stopPause() {
        log.error(String.valueOf(System.currentTimeMillis()));
        return "";
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
}
