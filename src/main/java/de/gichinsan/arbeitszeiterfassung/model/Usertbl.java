package de.gichinsan.arbeitszeiterfassung.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "usertbl")
public class Usertbl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;
}
