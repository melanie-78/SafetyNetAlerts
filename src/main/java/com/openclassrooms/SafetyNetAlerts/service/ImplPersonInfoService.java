package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ImplPersonInfoService implements PersonInfoService{

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonInfoMapper personInfoMapper;


    @Override
    public List<PersonInfoDto> getPersonInfo(String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();

        List<Person> allByLastName = personRepository.findAllByLastName(lastName);
        if(allByLastName.size() == 0){
            throw new NoSuchElementException("There is no person called "+lastName+ " in H2 dataBase");
        }

        allByLastName.forEach(person -> result.add(personInfoMapper.toDto(person)));
        return result;
    }
}
