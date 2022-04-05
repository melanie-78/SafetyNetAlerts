package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
//import com.openclassrooms.SafetyNetAlerts.web.dto.CommunityEmailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class CommunityEmailService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<String> getCommunityEmail() {

        List<Person> allByAddress = personRepository.findAll();
        List<String> emailList = allByAddress.stream().map(person -> {
            String email = person.getEmail();
            return email;
        }).collect(Collectors.toList());

        return emailList;
    }
}
