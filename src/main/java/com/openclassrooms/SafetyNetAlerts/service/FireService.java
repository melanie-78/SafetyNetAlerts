package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireUrlsDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FireUrlsMapper;
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
public class FireService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private FireUrlsMapper fireUrlsMapper;

    public List<FireDto> getFire(String address) {
        FireDto fireDto = new FireDto();
        List<FireDto> result = new ArrayList<>();

        Address byLabel = addressRepository.findByLabel(address);

        if(byLabel==null){
            throw new NoSuchElementException("this address does'nt exist in H2 dataBase");
        }else{
            List<String> stationList = byLabel.getFireStations()
                    .stream()
                    .map(fireStation -> fireStation.getStation())
                    .collect(Collectors.toList());
            fireDto.setStations(stationList);

            List<FireUrlsDto> fireUrlsDtoList = byLabel.getPersons()
                    .stream()
                    .map(person -> fireUrlsMapper.toDto(person))
                    .collect(Collectors.toList());
            fireDto.setFireUrlsDtoList(fireUrlsDtoList);

            result.add(fireDto);
        }

        return result;
    }
}
