package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.web.dto.ChildAlertDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonAgeMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChildAlertService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonAgeMapper personAgeMapper;
    @Autowired
    private ChildAlertDto childAlertDto;

    public List<ChildAlertDto> getChildAlert(String address) {
        List<ChildAlertDto> result = new ArrayList<>();

        Address byLabel = addressRepository.findByLabel(address);

        if(byLabel == null){
            throw new NoSuchElementException("this address does'nt exist in H2 dataBase");
        }else{
            List<PersonAgeDto> personAgeDtoList = byLabel.getPersons().stream()
                    .map(person -> personAgeMapper.toDto(person)).collect(Collectors.toList());

            List<PersonAgeDto> children = personAgeDtoList.stream()
                    .filter(personAgeDto -> personAgeDto.getAge() <= 18).collect(Collectors.toList());

            if(children == null){
                return null;
            }else {
                List<PersonAgeDto> adults = personAgeDtoList.stream()
                        .filter(personAgeDto -> personAgeDto.getAge() > 18).collect(Collectors.toList());
                childAlertDto.setChildList(children);
                childAlertDto.setAdultList(adults);
                result.add(childAlertDto);
                return result;
            }
        }
    }
}
