package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.ChildAlertDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.PersonAgeDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.PersonAgeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChildServiceTest {
    @InjectMocks
    private ChildAlertService cs;

    @Mock
    PersonAgeMapper personAgeMapper;
    @Mock
    AddressRepository addressRepository;
    @Mock
    ChildAlertDto childAlertDto;

    @Test
    public void getAlertChildThrowsExceptionTest(){
        String address = "1509 Culver St";
        when(addressRepository.findByLabel(any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->cs.getChildAlert(address));

        verify(addressRepository, Mockito.times(1)).findByLabel(any(String.class));
    }

    @Test
    public void getAlertChildTest(){
        String address = "1509 Culver St";
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        Person p1 = new Person (null, "Erica", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        Address a = new Address();
        a.setLabel("1509 Culver St");
        a.setCity("Culver");
        a.setZip("97451");
        a.setPersons(Arrays.asList(p, p1));
        when(addressRepository.findByLabel("1509 Culver St")).thenReturn(a);

        PersonAgeDto child1 = new PersonAgeDto("Erica", "Boyd",12);
        PersonAgeDto adult1 = new PersonAgeDto("John", "Boyd",37);

        when(personAgeMapper.toDto(p1)).thenReturn(child1);
        when(personAgeMapper.toDto(p)).thenReturn(adult1);


        ChildAlertDto ca = new ChildAlertDto();

        ca.setChildList(Arrays.asList(child1));
        ca.setAdultList(Arrays.asList(adult1));

        List<ChildAlertDto> expected = Arrays.asList(ca);


        List<ChildAlertDto> actual = cs.getChildAlert(address);


        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void getAlertChildNoChildrenTest(){
        String address = "1509 Culver St";
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        //Person p1 = new Person (null, "Erica", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        Address a = new Address();
        a.setLabel("1509 Culver St");
        a.setCity("Culver");
        a.setZip("97451");
        a.setPersons(Arrays.asList(p));
        when(addressRepository.findByLabel("1509 Culver St")).thenReturn(a);

        PersonAgeDto child1 = null;
        PersonAgeDto adult1 = new PersonAgeDto("John", "Boyd",37);
        when(personAgeMapper.toDto(p)).thenReturn(adult1);


        ChildAlertDto ca = new ChildAlertDto();


        ca.setChildList(Arrays.asList(child1));
        ca.setAdultList(Arrays.asList(adult1));

        List<ChildAlertDto> expected = Arrays.asList(ca);


        List<ChildAlertDto> actual = cs.getChildAlert(address);


        assertEquals(expected.size(), actual.size());
    }
}
