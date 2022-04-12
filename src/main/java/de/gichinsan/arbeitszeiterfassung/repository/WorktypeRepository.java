package de.gichinsan.arbeitszeiterfassung.repository;

import de.gichinsan.arbeitszeiterfassung.model.Worktype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorktypeRepository extends JpaRepository<Worktype, Long> {
}
