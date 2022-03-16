package com.openclassrooms.SafetyNetAlerts.data;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

}
