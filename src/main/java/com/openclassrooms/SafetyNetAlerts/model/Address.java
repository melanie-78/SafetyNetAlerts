package com.openclassrooms.SafetyNetAlerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_seq")
    @SequenceGenerator(name = "address_seq",sequenceName = "address_seq_table")
    private Long id;
    @Column(unique=true)
    private String label;
    private String city;
    private String zip;

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
