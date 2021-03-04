/**
 * This file is part of arbeitszeiterfassung.
 *
 * arbeitszeiterfassung is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * arbeitszeiterfassung is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * arbeitszeiterfassung. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;

import java.time.LocalDate;
import java.util.List;

public interface IWorkhoursService {

    boolean save(Workhours workhours);

    boolean saveUpdate(Workhours workhours);

    List<Workhours> getAllWorkinghours();

    Workhours findWorkhoursByDate(LocalDate date);

    List<Workhours> findByMonth(int month);


}
