package de.gichinsan.arbeitszeiterfassung.controller;

import de.gichinsan.arbeitszeiterfassung.service.CsvExportService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Data
@RestController
@Component(value = "export")
@RequestScoped
public class ExportController implements Serializable {

    private String myyear;
    private String mymonth;

    private final CsvExportService csvExportService;

    public ExportController(CsvExportService csvExportService) {
        this.csvExportService = csvExportService;
    }

    @RequestMapping(path = "/v1/csvreport")
    public void getAllWorkhoursInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"workhours.csv\"");
        csvExportService.writeWorkhoursToCsv(servletResponse.getWriter(), getMyyear(), getMymonth());
    }

    public void csvaction() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
