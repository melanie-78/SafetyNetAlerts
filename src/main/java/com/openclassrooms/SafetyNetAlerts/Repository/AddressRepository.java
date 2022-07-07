package com.openclassrooms.SafetyNetAlerts.Repository;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByLabel(String label);
    List<Address> findByCity(String city);
}
