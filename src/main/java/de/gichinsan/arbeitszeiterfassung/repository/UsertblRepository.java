package de.gichinsan.arbeitszeiterfassung.repository;

import de.gichinsan.arbeitszeiterfassung.model.Employee;
import de.gichinsan.arbeitszeiterfassung.model.Usertbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsertblRepository extends JpaRepository<Usertbl, Long> {

    Usertbl findByUsername(String username);

}
