package de.gichinsan.arbeitszeiterfassung.service;

import de.gichinsan.arbeitszeiterfassung.model.UserPrincipal;
import de.gichinsan.arbeitszeiterfassung.model.Usertbl;
import de.gichinsan.arbeitszeiterfassung.repository.UsertblRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UsertblService implements UserDetailsService, IUsertblService {

    @Autowired
    private UsertblRepository usertblRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException{


        Usertbl user = usertblRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

    @Override
    public Usertbl createNewUSer(Usertbl usertbl) {
        usertblRepository.save(usertbl);
        return usertbl;
    }
}
