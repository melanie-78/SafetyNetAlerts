package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.PhoneAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PhoneAlertService phoneAlertService;

    @Test
    public void testGetPhoneAlert() throws Exception{
        Person person = new Person();
        person.setPhone("841-874-6512");
        Person person2 = new Person();
        person2.setPhone("841-874-8547");
        Person person3 = new Person();
        person3.setPhone("841-874-7462");
        Address address = new Address();
        address.setPersons(Collections.singletonList(person));
        Address address2 = new Address();
        address2.setPersons(Arrays.asList(person2, person3));
        List<String> list= Arrays.asList("841-874-6512", "841-874-8547", "841-874-7462");
        String station = "1";

        when(phoneAlertService.getPhoneAlert(any(String.class))).thenReturn(list);

        mockMvc.perform(get("/phonealert").param("station", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("841-874-6512")));
    }

    @Test
    public void testGetPhoneAlertThrowsException() throws Exception{
        String station = "10";

        when(phoneAlertService.getPhoneAlert(station)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/phonealert").param("station", station))
                .andExpect(status().isNotFound());
    }
}
