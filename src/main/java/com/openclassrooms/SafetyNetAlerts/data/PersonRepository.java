package com.openclassrooms.SafetyNetAlerts.data;


import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByFirstNameAndLastName(String firstName, String lastName);
}