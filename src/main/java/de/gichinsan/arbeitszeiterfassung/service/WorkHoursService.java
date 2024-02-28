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
package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import de.gichinsan.arbeitszeiterfassung.repository.ArbeitszeitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class WorkHoursService implements IWorkhoursService {

    @Autowired
    private ArbeitszeitRepository repository;

    @Override
    public boolean save(Workhours workhours) {
        //Check before save
        var exists = repository.findWorkhoursByDate(workhours.getDate(), workhours.getDate().getYear());
        if (exists == null) {
            repository.save(workhours);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveUpdate(Workhours workhours) {
        repository.save(workhours);
        return true;
    }

    @Override
    public List<Workhours> getAllWorkinghours() {
        return repository.findAll();
    }


    @Override
    public Workhours findWorkhoursByDate(LocalDate date) {
        return repository.findWorkhoursByDate(date, date.getYear());
    }

    @Override
    public List<Workhours> findByMonthOrderByDate(int month, int year) {
        return repository.findByMonthAndByYear(month, year);
    }

    @Override
    public void delete(Workhours workhours) {
        repository.delete(workhours);
    }

    @Override
    public String findByMonthSumByWorkingHours(int month, int year) {
        List<Workhours> monthly = repository.findByMonthSumByWorkingHours(month, year);
        int gesamtZeit = 0;

        for (int i = 0; i < monthly.size(); i++) {
            LocalTime ltObject3 = LocalTime.parse(monthly.get(i).getWorkingHours());
            gesamtZeit = gesamtZeit + (ltObject3.getHour() * 60) + ltObject3.getMinute();
        }

        return (gesamtZeit / 60) + ":" + (gesamtZeit % 60);
    }


}
