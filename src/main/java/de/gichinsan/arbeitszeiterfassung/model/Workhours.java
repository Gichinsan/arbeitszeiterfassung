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
package de.gichinsan.arbeitszeiterfassung.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "workhours")
public class Workhours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private int month;
    private int startTimeHours;
    private int startTimeMinutes;
    private int stopTimeHours;
    private int stopTimeMinutes;
    private int durationPauseHours;
    private int durationPauseMinutes;
    private String workingHours;
    private int worktype;
}
