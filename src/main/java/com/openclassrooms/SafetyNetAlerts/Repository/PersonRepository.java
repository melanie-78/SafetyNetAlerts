package com.openclassrooms.SafetyNetAlerts.Repository;


import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    void deleteAllByFirstNameAndLastName(String firstName, String lastName);

    List<Person> findAllByFirstNameAndLastName(String firstName, String lastName);
}