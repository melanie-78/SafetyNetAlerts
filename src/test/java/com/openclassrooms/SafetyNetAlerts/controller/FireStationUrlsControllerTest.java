package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.ImplFireStationUrlsService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUrlsInfosDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class FireStationUrlsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ImplFireStationUrlsService fireStationUrlsService;

    @Test
    public void testGetFireStationUrls() throws Exception{
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

        List<FireStationUrlsDto> list = Arrays.asList(fireStationUrlsDto);

        when(fireStationUrlsService.getFireStationUrls(station)).thenReturn(list);

        mockMvc.perform(get("/firestation").param("station", station))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireStationUrlsThrowsException() throws Exception{
        String station ="10";

        when(fireStationUrlsService.getFireStationUrls(station)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/firestation").param("station", station))
                .andExpect(status().isNotFound());
    }
}
