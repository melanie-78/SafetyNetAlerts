package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsInfosDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FireStationUrlsMapper;
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
public class FireStationUrlsService {
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private FireStationUrlsMapper fireStationUrlsMapper;
    @Autowired
    private FireStationUrlsDto fireStationUrlsDto;

    public List<FireStationUrlsDto> getFireStationUrls(String station) {
        List<FireStationUrlsDto> result = new ArrayList<>();

        FireStation byStation = fireStationRepository.findByStation(station);

        if (byStation == null) {
            throw new NoSuchElementException("This station does'nt exist in H2 dataBase");
        }

        List<Person> persons = byStation
                .getAddresses()
                .stream()
                .map(address -> address.getPersons())
                .flatMap(personList -> personList.stream())
                .collect(Collectors.toList());

        List<FireStationUrlsInfosDto> fireStationUrlsInfosDtoList = persons
                .stream()
                .map(person -> fireStationUrlsMapper.toDto(person))
                .collect(Collectors.toList());
        fireStationUrlsDto.setFireStationUrlsInfosList(fireStationUrlsInfosDtoList);

        int childrenNumber = fireStationUrlsInfosDtoList
                .stream()
                .filter(fireStationUrlsBisDto -> fireStationUrlsBisDto.getAge() <= 18)
                .collect(Collectors.toList())
                .size();
        int adultsNumber = fireStationUrlsInfosDtoList.stream()
                .filter(fireStationUrlsBisDto -> fireStationUrlsBisDto.getAge() > 18)
                .collect(Collectors.toList())
                .size();
        fireStationUrlsDto.setChildrenNumber(childrenNumber);
        fireStationUrlsDto.setAdultsNumber(adultsNumber);

        result.add(fireStationUrlsDto);

        return result;
    }
}
