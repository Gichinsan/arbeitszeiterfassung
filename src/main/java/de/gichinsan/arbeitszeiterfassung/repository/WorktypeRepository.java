package de.gichinsan.arbeitszeiterfassung.repository;

import de.gichinsan.arbeitszeiterfassung.model.Worktype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorktypeRepository extends JpaRepository<Worktype, Long> {

    @Query("select r.longDesc from Worktype r where r.worktype_id =  :wtypeId")
    String findByIntId(@Param("wtypeId") int wtypeId);


}


