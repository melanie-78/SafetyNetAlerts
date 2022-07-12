package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsInfosDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FireStationUrlsMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class FireStationUrlsServiceTest {
    @InjectMocks
    private ImplFireStationUrlsService fireStationUrlsService;

    @Mock
    FireStationRepository fireStationRepository;
    @Mock
    FireStationUrlsMapper fireStationUrlsMapper;
    @Mock
    FireStationUrlsDto fireStationUrlsDto;

    @Test
    public void getFireStationUrlsThrowsExceptionTest(){
        String station = "1";
        when(fireStationRepository.findByStation(any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->fireStationUrlsService.getFireStationUrls(station));

        verify(fireStationRepository, Mockito.times(1)).findByStation(any(String.class));
    }

    @Test
    public void getFireStationUrlsTest(){
        String station = "1";

        Address a = new Address();
        a.setLabel("1509 Culver St");
        a.setCity("Culver");
        a.setZip("97451");

        FireStation fireStation = new FireStation();
        fireStation.setStation(station);
        fireStation.getAddresses().add(a);

        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        MedicalRecord medicalRecord = new MedicalRecord(null,"03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"), null);
        p.setMedicalRecord(Arrays.asList(medicalRecord));

        a.setFireStations(Arrays.asList(fireStation));
        a.setPersons(Arrays.asList(p));

        FireStationUrlsInfosDto f = new FireStationUrlsInfosDto("John", "Boyd", "1509 Culver St", "841-874-6512", 37);

        FireStationUrlsDto fireStationUrlsDto = new FireStationUrlsDto();
        fireStationUrlsDto.setFireStationUrlsInfosList(Arrays.asList(f));
        fireStationUrlsDto.setAdultsNumber(1);
        fireStationUrlsDto.setChildrenNumber(0);

        when(fireStationRepository.findByStation(station)).thenReturn(fireStation);
        when(fireStationUrlsMapper.toDto(p)).thenReturn(f);

        List<FireStationUrlsDto> expected = Arrays.asList(fireStationUrlsDto);

        //WHEN
        List<FireStationUrlsDto> actual = fireStationUrlsService.getFireStationUrls(station);

        //THEN
        assertEquals(expected.size(), actual.size());
    }
}
