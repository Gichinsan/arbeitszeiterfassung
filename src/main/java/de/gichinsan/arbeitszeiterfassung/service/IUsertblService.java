package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.UserPrincipal;
import de.gichinsan.arbeitszeiterfassung.model.Usertbl;


public interface IUsertblService {

    UserPrincipal loadUserByUsername(String username);

    Usertbl createNewUSer(Usertbl usertbl);

}
