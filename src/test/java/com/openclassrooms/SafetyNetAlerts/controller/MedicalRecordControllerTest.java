package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.service.MedicalRecordService;
import com.openclassrooms.SafetyNetAlerts.web.controller.MedicalRecordController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService ms;

   @Test
    public void testPostMedicalRecord() throws Exception{
        doNothing().when(ms).saveMedicalRecord(any());

        mockMvc.perform(post("/medicalRecord")
                        .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostMedicalRecordThrowsException() throws Exception{
        doThrow(new IllegalArgumentException()).when(ms).saveMedicalRecord(any());

        mockMvc.perform(post("/medicalRecord")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception{
       doNothing().when(ms).deleteMedicalRecord(any(), any());

       String firstName = "Roger";
       String lastName = "Boyd";
       mockMvc.perform(delete("/medicalRecord")
               .param("firstName", firstName)
               .param("lastName", lastName))
               .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecordThrowsException() throws Exception{

        doThrow(new NoSuchElementException()).when(ms).deleteMedicalRecord(any(), any());

        String firstName = "Mel";
        String lastName = "Adj";
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception{
        doNothing().when(ms).updateMedicalRecord(any(), any(), any());

        String firstName = "Ron";
        String lastName = "Peters";

        mockMvc.perform(put("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .content("{\"birthdate\":\"16/06/1965\", \"medications\":[], \"allergies\":[\"nillacilan\"]}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMedicalRecordThrowsException() throws Exception{

        doThrow(new NoSuchElementException()).when(ms).updateMedicalRecord(any(), any(), any());

        String firstName = "Jean";
        String lastName = "Reneys";

        mockMvc.perform(put("/medicalRecord")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .content("{\"birthdate\":\"16/06/1965\", \"medications\":[], \"allergies\":[\"nillacilan\"]}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
