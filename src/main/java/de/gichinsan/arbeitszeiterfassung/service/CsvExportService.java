package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.repository.ArbeitszeitRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class CsvExportService {

    @Autowired
    private ArbeitszeitRepository repository;

    public CsvExportService(ArbeitszeitRepository repository) {
        this.repository = repository;
    }

    /**
     *
     * @param writer
     * @param year
     * @param month
     */
    public void writeWorkhoursToCsv(Writer writer, String year, String month) {

        if (month == null){
            month = "1";
        }

        List<Workhours> workhours = repository.findByMonthAndByYear(Integer.parseInt(month), Integer.parseInt(year));
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID","Date","Workhours"))) {
            for (Workhours workhour : workhours) {
                csvPrinter.printRecord(workhour.getId(),workhour.getDate(),workhour.getWorkingHours());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
