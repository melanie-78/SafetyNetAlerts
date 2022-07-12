package com.openclassrooms.SafetyNetAlerts.service;


import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FloodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ImplFloodService implements FloodService{
    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private FloodMapper floodMapper;

    @Override
    public List<FloodDto> getFlood(List<Integer> stations) {
        FloodDto floodDto = new FloodDto();

        List<FloodDto> result = new ArrayList<>();

        List<FireStation> byStation = fireStationRepository.findByStationIn(stations.stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));

        if(byStation.size() == 0){
            throw new NoSuchElementException("these stations don't exist in H2 database");
        }

        List<Address> addressList= byStation.stream()
                .map(fireStation -> fireStation.getAddresses())
                .flatMap(addressCollection -> addressCollection.stream())
                .collect(Collectors.toList());

        List<Person> persons = addressList.stream()
                .map(address -> address.getPersons())
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
