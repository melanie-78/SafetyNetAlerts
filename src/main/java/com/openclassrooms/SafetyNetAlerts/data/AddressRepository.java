package com.openclassrooms.SafetyNetAlerts.data;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByLabel(String label);
}
