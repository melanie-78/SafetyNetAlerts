package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FloodMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class FloodServiceTest {
    @InjectMocks
    private ImplFloodService floodService;

    @Mock
    FloodMapper floodMapper;

    @Mock
    FireStationRepository fireStationRepository;

    @Test
    public void getFloodThrowsExceptionTest(){
        // Given : on veut le flood sur une liste de station qui n'existe pas
        List<Integer> stations = Collections.singletonList(1);
        when(fireStationRepository.findByStationIn(any())).thenReturn(new ArrayList<>());

        // When : On appelle getFlood
        // Then : Une NoSuchElementException est levée
        assertThrows(NoSuchElementException.class, ()->floodService.getFlood(stations));

    }

    @Test
    public void getFloodTest(){
        List<Integer> stations = Arrays.asList(1);
        List<String> stationsList = stations.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        FloodDto floodDto = new FloodDto();
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        MedicalRecord medicalRecord = new MedicalRecord(null,"03/06/1984", Arrays.asList("pharmacol:5000mg"), Arrays.asList(), null);
        p.setMedicalRecord(Arrays.asList(medicalRecord));

        FloodPersonDto floodPersonDto = new FloodPersonDto("Boyd", "841-874-6512", "1509 Culver St", 37, Arrays.asList("pharmacol:5000mg"), Arrays.asList());
        Map<String, List<FloodPersonDto>> floodMap = new HashMap<String, List<FloodPersonDto>>();
        floodMap.put("1", Arrays.asList(floodPersonDto));

        floodDto.setFloodListMap(floodMap);
        List<FloodDto> expected = Arrays.asList(floodDto);

        Address address = new Address();
        address.setLabel("1509 Culver St");
        address.setCity("Culver");
        address.setZip("97451");
        address.setPersons(Arrays.asList(p));

        FireStation f = new FireStation();
        f.setStation("1");
        f.setAddresses(Arrays.asList(address));
        List<FireStation> fList = Arrays.asList(f);

        when(fireStationRepository.findByStationIn(stationsList)).thenReturn(fList);
        when(floodMapper.toDto(p)).thenReturn(floodPersonDto);


        List<FloodDto> actual = floodService.getFlood(stations);


        assertEquals(expected.size(), actual.size());

    }
}
