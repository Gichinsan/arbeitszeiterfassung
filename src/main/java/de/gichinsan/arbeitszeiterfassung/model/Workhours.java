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
package de.gichinsan.arbeitszeiterfassung.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "workhours")
public class Workhours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int startTimeHours;
    private int startTimeMinutes;
    private int stopTimeHours;
    private int stopTimeMinutes;
    private Long startPauseHours;
    private Long startPauseMinutes;
    private Long stopPauseHours;
    private Long stopPauseMinutes;

    private Date date;
}
