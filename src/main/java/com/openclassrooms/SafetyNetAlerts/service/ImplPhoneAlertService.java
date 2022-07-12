package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ImplPhoneAlertService implements PhoneAlertService {
    @Autowired
    private FireStationRepository fireStationRepository;

    @Override
    public List<String> getPhoneAlert(String station) {
        FireStation byStation = fireStationRepository.findByStation(station);
        if(byStation == null){
            throw new NoSuchElementException("this station does'nt exist in H2 dataBase");
        }
        List<Person> persons = byStation.getAddresses().stream()
                .map(address -> address.getPersons())
                .flatMap(personList -> personList.stream())
                .collect(Collectors.toList());

        List<String> phoneList = persons.stream().map(person -> {
            String phone = person.getPhone();
            return phone;
        }).collect(Collectors.toList());

        return phoneList;
    }
}
