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

import de.gichinsan.arbeitszeiterfassung.model.Worktype;
import de.gichinsan.arbeitszeiterfassung.repository.WorktypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class WorktypeService implements IWorktypeService {

    @Autowired
    private WorktypeRepository repository;

    @Override
    public List<Worktype> getAllWorktypes() {
        return repository.findAll();
    }
}
