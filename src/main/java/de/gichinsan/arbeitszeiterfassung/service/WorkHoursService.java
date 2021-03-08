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
        var exists = repository.findWorkhoursByDate(workhours.getDate());
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
        return repository.findWorkhoursByDate(date);
    }

    @Override
    public List<Workhours> findByMonth(int month) {
        return repository.findByMonth(month);
    }

    @Override
    public void delete(Workhours workhours) {
        repository.delete(workhours);
    }


}
