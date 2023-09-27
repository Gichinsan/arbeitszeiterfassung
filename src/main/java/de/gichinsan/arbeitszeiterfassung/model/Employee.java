package de.gichinsan.arbeitszeiterfassung.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private int weeklyWorkinghours;
    private int maxDailyWorkinghours;
    private int pausemgmt;



}
