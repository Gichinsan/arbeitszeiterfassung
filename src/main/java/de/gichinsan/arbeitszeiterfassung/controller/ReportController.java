package de.gichinsan.arbeitszeiterfassung.controller;

import de.gichinsan.arbeitszeiterfassung.model.Employee;
import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.service.EmployeeService;
import de.gichinsan.arbeitszeiterfassung.service.WorkHoursService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@RestController
@Component(value = "report")
@Slf4j
@Transactional
@Data
public class ReportController implements Serializable {

    private String monthNow;
    private String monthPrev;
    private String monthPrePrev;
    private String tabIndex;

    @Autowired
    private WorkHoursService service;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/v1/reports", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Workhours> getWorkinghours() {
        return service.getAllWorkinghours();
    }

    @RequestMapping(value = "/v1/reportByDay", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public Workhours getWorkinghours(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return service.findWorkhoursByDate(date);
    }

    @RequestMapping(value = "/v1/reportByMonth", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Workhours> getWorkinghoursByMonth(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year) {
        return service.findByMonthOrderByDate(month, year);
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
        return service.findByMonthOrderByDate(prevMonth.getMonth().getValue(), year);
    }

    public String getTabIndex() {
        return "2";
    }


    @RequestMapping(value = "/v1/sumByMonth", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String getSumByMonth(@RequestParam(value = "month") int month) {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int iMonth = date.getMonthValue() - month;
        if (iMonth < 1) {
            year = year - 1;
            iMonth = 13 - month;
        }
        LocalDate prevMonth = LocalDate.of(year, iMonth, date.getDayOfMonth());
        return service.findByMonthSumByWorkingHours(prevMonth.getMonth().getValue(), year);
    }

    @RequestMapping(value = "/v1/workdaysByMonth", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String getWorkDaysOfMonth(@RequestParam(value = "year") int year, @RequestParam(value = "month") int month) {

        RestTemplate rt = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:9889/v1/calcDayofMonth?year=" + year + "&month=" + month;

        ResponseEntity<String> response
                = rt.getForEntity(fooResourceUrl, String.class);

        return response.getBody();
    }

    /**
     * @return
     */
    public String getSollWorkinghours(int month) {
        try {
            LocalDate date = LocalDate.now();
            int year = date.getYear();
            int iMonth = date.getMonthValue() - month;
            if (iMonth < 1) {
                year = year - 1;
                iMonth = 13 - month;
            }

            int wdi = Integer.parseInt(getWorkDaysOfMonth(year, iMonth));
            List<Employee> employeeList = employeeService.getAllEmployee();
            int wwhours = employeeList.get(0).getWeeklyWorkinghours();

            int dailyhours = wwhours / 5;
            int zwZeit = wdi * dailyhours;

            return zwZeit + ":" + "00";
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }

    }

    @RequestMapping(value = "/v1/employeeData", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployeeData() {
        return employeeService.getAllEmployee();
    }
}
