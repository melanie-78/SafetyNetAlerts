package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.service.ImplFloodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class FloodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ImplFloodService floodService;

    @Test
    public void testFloodController() throws Exception{
        when(floodService.getFlood(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/flood/stations?stations=1"))
                .andExpect(status().isOk());

    }
}
