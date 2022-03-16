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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="medicalRecord_seq")
    @SequenceGenerator(name = "medicalRecord_seq",sequenceName = "medicalRecord_seq_table")

    private Long id;
    private String birthdate;
    @ElementCollection
    private Collection<String> medications;
    @ElementCollection
    private Collection<String> allergies;

    @ManyToOne (cascade = CascadeType.ALL)
    private Person person;
}
