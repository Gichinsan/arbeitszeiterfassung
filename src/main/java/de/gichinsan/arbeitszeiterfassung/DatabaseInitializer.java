package de.gichinsan.arbeitszeiterfassung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Initialisierung der worktype-Tabelle
        initializeWorktypeTable();

        // Initialisierung der roles-Tabelle
        initializeRolesTable();

        // Initialisierung der usertbl-Tabelle
        initializeUsertblTable();

        // Initialisierung der users_roles-Tabelle
        initializeUsersRolesTable();
    }

    private void initializeWorktypeTable() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM worktype WHERE worktype_id = 1", Integer.class);
        if (count == 0) {
            jdbcTemplate.update("INSERT INTO worktype (worktype_id, longDesc, shortDesc) VALUES (?, ?, ?)", 1, "Arbeitszeit", "Arbeitszeit");
            jdbcTemplate.update("INSERT INTO worktype (worktype_id, longDesc, shortDesc) VALUES (?, ?, ?)", 2, "Urlaub", "Urlaub");
            jdbcTemplate.update("INSERT INTO worktype (worktype_id, longDesc, shortDesc) VALUES (?, ?, ?)", 3, "Gleitzeit", "Gleitzeit");
            jdbcTemplate.update("INSERT INTO worktype (worktype_id, longDesc, shortDesc) VALUES (?, ?, ?)", 4, "Krankheit", "Krank");
        }
    }

    private void initializeRolesTable() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM roles WHERE name = 'USER'", Integer.class);
        if (count == 0) {
            jdbcTemplate.update("INSERT INTO roles (name) VALUES (?)", "USER");
            jdbcTemplate.update("INSERT INTO roles (name) VALUES (?)", "CREATOR");
            jdbcTemplate.update("INSERT INTO roles (name) VALUES (?)", "EDITOR");
            jdbcTemplate.update("INSERT INTO roles (name) VALUES (?)", "ADMIN");
        }
    }

    private void initializeUsertblTable() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usertbl WHERE user_id = 1", Integer.class);
        if (count == 0) {
            jdbcTemplate.update("INSERT INTO usertbl (user_id, username, password, enabled) VALUES (?, ?, ?, ?)", 1, "admin", "$2a$12$A7C/P3XXuwNoGHdy.K957.G75UzxDXtAUv9xIVDekI2K8bLFNNG8a", true);
        }
    }

    private void initializeUsersRolesTable() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users_roles WHERE user_id = 1 AND role_id = 4", Integer.class);
        if (count == 0) {
            jdbcTemplate.update("INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)", 1, 4);
        }
    }
}
