package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.service.ChildAlertService;
import com.openclassrooms.SafetyNetAlerts.web.dto.ChildAlertDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
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
public class ChildAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ChildAlertService childAlertService;

    @Test
    public void testGetChildAlert() throws Exception{
        String address = "1509 Culver St";
        PersonAgeDto child1 = new PersonAgeDto("Erica", "Boyd",12);
        PersonAgeDto adult1 = new PersonAgeDto("John", "Boyd",37);

        ChildAlertDto ca = new ChildAlertDto();

        ca.setChildList(Arrays.asList(child1));
        ca.setAdultList(Arrays.asList(adult1));

        List<ChildAlertDto> list = Arrays.asList(ca);

        when(childAlertService.getChildAlert(address)).thenReturn(list);

        mockMvc.perform(get("/childalert").param("address", address))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChildAlertThrowsException() throws Exception{
        String address = "1509 Culver St";

        when(childAlertService.getChildAlert(address)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/childalert").param("address", address))
                .andExpect(status().isNotFound());
    }
}
