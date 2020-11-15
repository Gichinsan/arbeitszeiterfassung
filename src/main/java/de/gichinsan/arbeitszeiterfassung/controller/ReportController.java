package de.gichinsan.arbeitszeiterfassung.controller;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.service.WorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@RestController
@Component(value = "report")
public class ReportController implements Serializable {

    @Autowired
    private WorkHoursService service;

    @RequestMapping(value = "/report", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Workhours> getWorkinghours() {
        return service.getAllWorkinghours();
    }

    @RequestMapping(value = "/report/{date}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Workhours getWorkinghours(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return service.findWorkhoursByDate(date);
    }

}
