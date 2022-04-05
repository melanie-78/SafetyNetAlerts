package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.PersonInfoRepository;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonInfoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoService {

    @Autowired
    private PersonInfoRepository personInfoRepository;
    @Autowired
    private PersonInfoDto personInfoDto;
    @Autowired
    private PersonInfoMapper personInfoMapper;

    public List<PersonInfoDto> getPersonInfo(String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();

        List<Person> allByLastName = personInfoRepository.findAllByLastName(lastName);
        if(allByLastName.size() == 0){
            throw new NoSuchElementException("There is no person called "+lastName+ " in H2 dataBase");
        }

        allByLastName.forEach(person -> result.add(personInfoMapper.toDto(person)));
        return result;
    }
}
