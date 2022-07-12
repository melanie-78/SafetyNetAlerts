package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.service.ImplFireStationService;
import com.openclassrooms.SafetyNetAlerts.web.controller.FireStationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImplFireStationService fs;


    @Test
    public void testDeleteFireStation() throws Exception{
        doNothing().when(fs).deleteFireStation(any());

        mockMvc.perform(delete("/firestation")
                        .content("{\"address\": \"908 73rd St\", \"station\": \"1\"\n }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFireStationThrowsException() throws Exception{
        doThrow(new NoSuchElementException()).when(fs).deleteFireStation(any());

        mockMvc.perform(delete("/firestation")
                        .content("{\"address\": \"908 73rd St\", \"station\": \"1\"\n }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFireStation() throws Exception {
        doNothing().when(fs).updateFireStation(any());

        mockMvc.perform(put("/firestation")
                        .content("{\"address\": \"908 73rd St\", \"station\": \"1\", \"newStation\": \"8\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFireStationThrowsException() throws Exception {
        doThrow(new NoSuchElementException()).when(fs).updateFireStation(any());

        mockMvc.perform(put("/firestation")
                        .content("{\"address\": \"908 73rd St\", \"station\": \"1\", \"newStation\": \"8\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostFireStation() throws Exception {
        doNothing().when(fs).saveFireStation(any());

        mockMvc.perform(post("/firestation")
                        .content("{\"address\": \"908 73rd St\", \"station\": \"6\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
