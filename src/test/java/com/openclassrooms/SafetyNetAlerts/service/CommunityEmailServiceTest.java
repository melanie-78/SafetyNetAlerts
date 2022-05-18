package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class CommunityEmailServiceTest {
    @InjectMocks
    private CommunityEmailService communityEmailService;

    @Mock
    PersonRepository personRepository;

    @Test
    public void getCommunityEmailTest(){
        List<String> expected = Arrays.asList("soph@email.com","ward@email.com");
        Person p = new Person();
        p.setEmail("soph@email.com");
        Person p1 = new Person();
        p1.setEmail("ward@email.com");

        List<Person> pList = Arrays.asList(p, p1);

        when(personRepository.findAll()).thenReturn(pList);

        List<String> actual = communityEmailService.getCommunityEmail();

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.contains("soph@email.com"));
        assertTrue(actual.contains("ward@email.com"));
     }
}
