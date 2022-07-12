package com.openclassrooms.SafetyNetAlerts.controller;


import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.ImplFireService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireUrlsDto;
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
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class FireControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ImplFireService fireService;

    @Test
    public void testGetFire() throws Exception{
        String address = "1509 Culver St";
        Address a = new Address();
        FireStation f = new FireStation();
        f.setAddresses(Arrays.asList(a));
        f.setStation("1");
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        a.setLabel("1509 Culver St");
        a.setCity("Culver");
        a.setZip("97451");
        a.setFireStations(Arrays.asList(f));
        a.setPersons(Arrays.asList(p));

        FireUrlsDto fireUrlsDto = new FireUrlsDto("Boyd",32,"841-874-6512", Arrays.asList("pharmacol:5000mg"));

        FireDto fireDto = new FireDto(Arrays.asList("1"), Arrays.asList(fireUrlsDto));

        List<FireDto> list = Arrays.asList(fireDto);

        when(fireService.getFire(address)).thenReturn(list);

        mockMvc.perform(get("/fire").param("address", address))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireThrowsException() throws Exception{
        String address ="12 Allée Jean";

        when(fireService.getFire(address)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/fire").param("address", address))
                .andExpect(status().isNotFound());
    }
}
