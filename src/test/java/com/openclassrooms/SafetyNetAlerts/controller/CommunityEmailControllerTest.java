package com.openclassrooms.SafetyNetAlerts.controller;

import com.openclassrooms.SafetyNetAlerts.service.CommunityEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommunityEmailControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    CommunityEmailService communityEmailService;

    @Test
    public void testGetCommunityEmail() throws Exception {
        String city = "Culver";
        String email = "soph@email.com";
        List<String> list = Arrays.asList(email);
        when(communityEmailService.getCommunityEmail(city)).thenReturn(list);

        mockMvc.perform(get("/communityemail").param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("soph@email.com")));
    }
}
