package com.openclassrooms.SafetyNetAlerts.service;


import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FloodMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FloodService {
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private FloodDto floodDto;
    @Autowired
    private FloodMapper floodMapper;

    public List<FloodDto> getFlood(String station) {
        List<FloodDto> result = new ArrayList<>();

        FireStation byStation = fireStationRepository.findByStation(station);

        if(byStation == null){
            throw new NoSuchElementException("this station does'nt exist");
        }
        Collection<Address> addresses = byStation.getAddresses();
        ArrayList<Address> addressList = new ArrayList<>(addresses);

        List<Person> persons = addressList.stream().map(address -> address.getPersons())
                .flatMap(personList -> personList.stream())
                .collect(Collectors.toList());

        Map<String, List<FloodPersonDto>> floodListMap = persons.stream()
                .map(person -> floodMapper.toDto(person))
                .collect(groupingBy(floodPersonDto -> floodPersonDto.getAddress()));

        floodDto.setFloodListMap(floodListMap);

        result.add(floodDto);
        return result;
    }
}
