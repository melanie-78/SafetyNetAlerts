package com.openclassrooms.SafetyNetAlerts.Repository;

import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloodRepository extends JpaRepository<FireStation, Long> {
    List<FireStation> findByStationIn(List<String> stations);
}
