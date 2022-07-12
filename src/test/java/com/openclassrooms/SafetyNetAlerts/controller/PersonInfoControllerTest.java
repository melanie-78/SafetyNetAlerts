package com.openclassrooms.SafetyNetAlerts.controller;


import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.ImplPersonInfoService;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ImplPersonInfoService personInfoService;

    @Test
    public void testGetPersonInfo() throws Exception{
        String lastName = "Cadigan";
        Person p = new Person(null,"Eric","Cadigan",new Address(null, "951 LoneTree Rd", "Culver", "97451", null, null ), "841-874-6512", "gramps@email.com", null );
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate("08/06/1945");
        medicalRecord.setMedications(Arrays.asList("tradoxidine:400mg"));
        medicalRecord.setAllergies(new ArrayList());
        p.setMedicalRecord(Arrays.asList(medicalRecord));

        PersonInfoDto personInfoDto = new PersonInfoDto("Cadigan","Eric","951 LoneTree Rd","gramps@email.com",76, Arrays.asList("tradoxidine:400mg"), new ArrayList());
        List<PersonInfoDto> list = Arrays.asList(personInfoDto);

        when(personInfoService.getPersonInfo(lastName)).thenReturn(list);

        mockMvc.perform(get("/personinfo").param("lastName", lastName))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfoThrowsException() throws Exception{
        String lastName ="Mel";

        when(personInfoService.getPersonInfo(lastName)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/personinfo").param("lastName", lastName))
                .andExpect(status().isNotFound());
    }

}
