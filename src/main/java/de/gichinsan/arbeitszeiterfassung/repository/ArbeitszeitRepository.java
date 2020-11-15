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
package de.gichinsan.arbeitszeiterfassung.repository;

import de.gichinsan.arbeitszeiterfassung.model.Workhours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ArbeitszeitRepository extends JpaRepository<Workhours, Long> {

    @Query("select r from Workhours r where r.date = :workDate")
    Workhours findWorkhoursByDate(@Param("workDate") LocalDate date);
}
