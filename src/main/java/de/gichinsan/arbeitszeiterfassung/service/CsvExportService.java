package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.repository.ArbeitszeitRepository;
import de.gichinsan.arbeitszeiterfassung.repository.WorktypeRepository;
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
    private ArbeitszeitRepository arbeitszeitRepository;

    @Autowired
    private WorktypeRepository worktypeRepository;

    public CsvExportService(ArbeitszeitRepository arbeitszeitRepository) {
        this.arbeitszeitRepository = arbeitszeitRepository;
    }

    /**
     * @param writer
     * @param year
     * @param month
     */
    public void writeWorkhoursToCsv(Writer writer, String year, String month) {

        if (month == null) {
            month = "1";
        }

        List<Workhours> workhours = arbeitszeitRepository.findByMonthAndByYear(Integer.parseInt(month), Integer.parseInt(year));
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Date", "Workhours", "Art"))) {
            for (Workhours workhour : workhours) {

                String workType = worktypeRepository.findByIntId(workhour.getWorktype());
                if (!workType.isEmpty()) {
                    csvPrinter.printRecord(workhour.getId(), workhour.getDate(), workhour.getWorkingHours(), workType);
                }
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
