package com.openclassrooms.SafetyNetAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="person_seq")
    @SequenceGenerator(name = "person_seq",sequenceName = "person_seq_table")
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToOne(optional = false, cascade = CascadeType.ALL )
    private Address address;

    private String phone;
    private String email;
}
