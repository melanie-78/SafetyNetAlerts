package com.openclassrooms.SafetyNetAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "medicalRecord")
@NoArgsConstructor
@AllArgsConstructor

public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String birthdate;
    @ElementCollection
    private Collection<String> medications;
    @ElementCollection
    private Collection<String> allergies;

    @ManyToOne
    private Person person;
}
