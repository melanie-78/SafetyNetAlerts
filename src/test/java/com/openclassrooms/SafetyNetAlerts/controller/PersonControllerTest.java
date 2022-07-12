package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.service.ImplPersonService;
import com.openclassrooms.SafetyNetAlerts.web.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImplPersonService ps;


    @Test
    public void testPutPerson() throws Exception {
        doNothing().when(ps).update(any(),any(),any());

        String firstName = "Ron";
        String lastName = "Peters";
        mockMvc.perform(delete("/person")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .content("{\"address\":\"644 Gershwin Cir\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutPersonThrowsException() throws Exception {
        doThrow(new NoSuchElementException()).when(ps).update(any(),any(),any());

        String firstName = "Ron";
        String lastName = "Peters";
        mockMvc.perform(put("/person")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .content("{\n" +
                                "  \"firstName\": \"Melanie\",\n" +
                                "  \"lastName\": \"Bodi\",\n" +
                                "  \"address\": \"1509 Culver St\",\n" +
                                "  \"city\": \"Culver\",\n" +
                                "  \"zip\": \"97451\",\n" +
                                "  \"phone\": \"841-874-6512\",\n" +
                                "  \"email\": \"mel@email.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostPerson() throws Exception {
        doNothing().when(ps).savePerson(any());

        mockMvc.perform(post("/person")
                        .content("{\n" +
                                "  \"firstName\": \"Melanie\",\n" +
                                "  \"lastName\": \"Bodi\",\n" +
                                "  \"address\": \"1509 Culver St\",\n" +
                                "  \"city\": \"Culver\",\n" +
                                "  \"zip\": \"97451\",\n" +
                                "  \"phone\": \"841-874-6512\",\n" +
                                "  \"email\": \"mel@email.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        String firstName = "John";
        String lastName = "Boyd";
        mockMvc.perform(delete("/person")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonThrowsException() throws Exception {
        doThrow(new NoSuchElementException()).when(ps).deletePerson(any(), any());

        String firstName = "Jeanne";
        String lastName = "Lee";
        mockMvc.perform(delete("/person")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isNotFound());
    }
}
