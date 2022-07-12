package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertServiceTest {
    @InjectMocks
    private ImplPhoneAlertService phoneAlertService;

    @Mock
    FireStationRepository fireStationRepository;

    @Test
    public void getPhoneAlertThrowsExceptionTest(){
        String station = "1";
        when(fireStationRepository.findByStation(any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->phoneAlertService.getPhoneAlert(station));

        verify(fireStationRepository, Mockito.times(1)).findByStation(any(String.class));
    }

    @Test
    public void getPhoneAlertTest(){
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
        List<String> expected = Arrays.asList("841-874-6512", "841-874-8547", "841-874-7462");
        String station = "1";
        FireStation fireStation = new FireStation(null, "1", Arrays.asList(address, address2));
        when(fireStationRepository.findByStation(any(String.class))).thenReturn(fireStation);

        List<String> actual = phoneAlertService.getPhoneAlert(station);

        assertTrue(actual.contains("841-874-6512"));
        assertTrue(actual.contains("841-874-8547"));
        assertTrue(actual.contains("841-874-7462"));
    }
}
