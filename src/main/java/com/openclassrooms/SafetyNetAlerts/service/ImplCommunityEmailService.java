package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ImplCommunityEmailService implements CommunityEmailService{
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<String> getCommunityEmail(String city) {
        List<Address> byCity = addressRepository.findByCity(city);

        if (byCity.size()==0){
            throw new NoSuchElementException("this city doesn't exist in database");
        }
        List<Person> allByAddress = personRepository.findAll();
        List<String> emailList = allByAddress.stream().map(person -> {
            String email = person.getEmail();
            return email;
        }).collect(Collectors.toList());

        return emailList;
    }
}
