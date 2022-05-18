package com.openclassrooms.SafetyNetAlerts.Repository;

import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonInfoRepository extends JpaRepository<Person, Long> {

    //Use for PersonInfo's Urls
    List<Person> findAllByLastName(String lastName);
}
