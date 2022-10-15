package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.UserPrincipal;


public interface IUsertblService {

    UserPrincipal loadUserByUsername(String username);

}
