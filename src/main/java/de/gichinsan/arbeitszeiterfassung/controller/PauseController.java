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
package de.gichinsan.arbeitszeiterfassung.controller;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

@Data
@Component(value = "pause")
@RestController
@RequestScoped
public class PauseController implements Serializable {

    private Instant start;
    private Instant stop;
    private int minutes;
    private int pauseRealTime;

    @PostMapping(value = "/v1/startPause")
    public String startPause() {
        start = Instant.now();
        return "/startpause";
    }

    @PostMapping(value = "/v1/stopPause")
    public String stopPause() {
        stop = Instant.now();
        Duration d = Duration.between(start, stop);
        minutes = d.toMinutesPart();
        return "/stoppause";
    }

    @RequestMapping(value = "/v1/pausetime", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public int getPauseRealTime() {
        if (minutes > 0) {
            return minutes;
        } else {
            return 0;
        }
    }


}
