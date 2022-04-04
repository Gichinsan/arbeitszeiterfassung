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
@Component(value = "editctl")
@RestController
@RequestScoped
@Slf4j
public class EditController implements Serializable {

    private LocalDate arbeitstag;
    private LocalDate searcharbeitstag;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private LocalTime minTime;
    private LocalTime maxTime;
    private LocalTime pause;
    private String berechnung;

    @Autowired
    private WorkHoursService service;
    private Workhours uwh;

    @PostConstruct
    public void init() {
        minTime = LocalTime.of(6, 0);
        maxTime = LocalTime.of(19, 0);
    }

    public String searchWorkingDay() {
        uwh = new Workhours();
        try {
            uwh = service.findWorkhoursByDate(searcharbeitstag);

            setStartZeit(LocalTime.of(uwh.getStartTimeHours(), uwh.getStartTimeMinutes()));
            setPause(LocalTime.of(uwh.getDurationPauseHours(), uwh.getDurationPauseMinutes()));
            setEndZeit(LocalTime.of(uwh.getStopTimeHours(), uwh.getStopTimeMinutes()));
            setBerechnung(uwh.getWorkingHours());
        } catch (Exception e) {
            addErrorMessage("Für diesen Tag exisiteren keine einträge! " + searcharbeitstag);
            log.error(e.getLocalizedMessage());
        }
        return "/edit";
    }

    public String updateAction() {

        Duration duration = Duration.between(endZeit, startZeit);
        long diff = Math.abs(duration.toMinutes());

        Duration mduration = Duration.ofMinutes(pause.getMinute());
        Duration hduration = Duration.ofHours(pause.getHour());

        String realDiff = LocalTime.MIN.plus(
                Duration.ofMinutes(diff).minus(mduration).minus(hduration)
        ).toString();

        setBerechnung(realDiff);

        uwh.setDate(searcharbeitstag);
        uwh.setMonth(searcharbeitstag.getMonthValue());
        uwh.setStartTimeHours(startZeit.getHour());
        uwh.setStartTimeMinutes(startZeit.getMinute());
        uwh.setStopTimeHours(endZeit.getHour());
        uwh.setStopTimeMinutes(endZeit.getMinute());
        uwh.setDurationPauseHours(pause.getHour());
        uwh.setDurationPauseMinutes(pause.getMinute());
        uwh.setWorkingHours(realDiff);
        if (service.saveUpdate(uwh)) {
            addMessage("Arbeitstag erfolgreich geupdated");
            return "/edit";
        } else {
            addErrorMessage("Für diesen Tag exisiteren keine einträge! " + searcharbeitstag);
            return "/edit";
        }
    }

    public String deleteAction() {
        try {
            service.delete(uwh);
            addMessage("Arbeitstag erfolgreich gelöscht");
            setStartZeit(null);
            setEndZeit(null);
            setPause(null);
            setSearcharbeitstag(null);
            setBerechnung(null);
            return "/edit";
        } catch (Exception e) {
            addErrorMessage("Für diesen Tag exisiteren keine einträge! " + searcharbeitstag);
            log.error(e.getLocalizedMessage());
            return "/edit";
        }
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
