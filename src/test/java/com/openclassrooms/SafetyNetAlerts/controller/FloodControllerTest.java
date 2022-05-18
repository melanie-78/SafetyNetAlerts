package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.FloodService;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FloodPersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class FloodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FloodService floodService;

    @Test
    public void testFloodController() throws Exception{
        when(floodService.getFlood(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/flood/stations?stations=1"))
                .andExpect(status().isOk());

    }
}
