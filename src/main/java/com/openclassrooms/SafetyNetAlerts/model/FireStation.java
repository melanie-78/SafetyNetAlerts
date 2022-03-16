package com.openclassrooms.SafetyNetAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "fireStation")
@NoArgsConstructor
@AllArgsConstructor

public class FireStation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="fireStation_seq")
    @SequenceGenerator(name = "fireStation_seq",sequenceName = "fireStation_seq_table")

    private Long id;
    private String station;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Address> addresses = new ArrayList<>();
}
