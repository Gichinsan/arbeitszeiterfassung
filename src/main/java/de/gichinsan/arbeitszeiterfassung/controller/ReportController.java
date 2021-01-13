package de.gichinsan.arbeitszeiterfassung.controller;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.service.WorkHoursService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@RestController
@Component(value = "report")
@Slf4j
public class ReportController implements Serializable {

    private String monthNow;
    private String monthPrev;
    private String monthPrePrev;
    private String tabIndex;

    @Autowired
    private WorkHoursService service;

    @RequestMapping(value = "/v1/reports", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Workhours> getWorkinghours() {
        return service.getAllWorkinghours();
    }

    @RequestMapping(value = "/v1/reportByDay", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Workhours getWorkinghours(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return service.findWorkhoursByDate(date);
    }

    @RequestMapping(value = "/v1/reportByMonth", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Workhours> getWorkinghoursByMonth(@RequestParam(value = "month") int month) {
        return service.findByMonth(month);
    }

    public String getMonthByName(String value) {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int iMonth = date.getMonthValue() - Integer.parseInt(value);
        if (iMonth < 1) {
            year = year - 1;
            iMonth = 13 - Integer.parseInt(value);
        }
        LocalDate prevMonth = LocalDate.of(year, iMonth, date.getDayOfMonth());
        return prevMonth.getMonth().name();
    }

    public List<Workhours> getWorkinghoursByMonthValue(String value) {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int iMonth = date.getMonthValue() - Integer.parseInt(value);
        if (iMonth < 1) {
            year = year - 1;
            iMonth = 13 - Integer.parseInt(value);
        }
        LocalDate prevMonth = LocalDate.of(year, iMonth, date.getDayOfMonth());
        return service.findByMonth(prevMonth.getMonth().getValue());
    }

    public String getTabIndex() {
        return "2";
    }
}
