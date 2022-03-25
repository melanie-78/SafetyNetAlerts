package com.openclassrooms.SafetyNetAlerts.Repository;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    FireStation findByStation(String station);

    FireStation findByAddresses(Address byLabel);
}
