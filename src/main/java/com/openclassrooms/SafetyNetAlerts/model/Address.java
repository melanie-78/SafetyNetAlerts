package com.openclassrooms.SafetyNetAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String label;
    private String city;
    private String zip;

    @OneToMany(mappedBy = "address")
    private List<Person> persons;

    @ManyToMany(mappedBy = "addresses")
    private Collection<FireStation> fireStations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return label.equals(address.getLabel()) && city.equals(address.getCity()) && zip.equals(address.getZip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, city, zip);
    }
}
