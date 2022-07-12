package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireDto;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireUrlsDto;
import com.openclassrooms.SafetyNetAlerts.web.mapper.FireUrlsMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class FireServiceTest {
    @InjectMocks
    private ImplFireService fireService;

    @Mock
    AddressRepository addressRepository;
    @Mock
    FireUrlsMapper fireUrlsMapper;

    @Test
    public void getFireThrowsExceptionTest(){
        String address = "1509 Culver St";
        when(addressRepository.findByLabel(any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->fireService.getFire(address));

        verify(addressRepository, Mockito.times(1)).findByLabel(any(String.class));
    }

    @Test
    public void getFireTest(){
        String address = "1509 Culver St";
        Address a = new Address();
        FireStation f = new FireStation();
        f.setAddresses(Arrays.asList(a));
        f.setStation("1");
        Person p = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com", null);
        a.setLabel("1509 Culver St");
        a.setCity("Culver");
        a.setZip("97451");
        a.setFireStations(Arrays.asList(f));
        a.setPersons(Arrays.asList(p));

        FireUrlsDto fireUrlsDto = new FireUrlsDto("Boyd",32,"841-874-6512", Arrays.asList("pharmacol:5000mg"));

        FireDto fireDto = new FireDto(Arrays.asList("1"), Arrays.asList(fireUrlsDto));

        List<FireDto> expected = Arrays.asList(fireDto);

        when(addressRepository.findByLabel("1509 Culver St")).thenReturn(a);
        when(fireUrlsMapper.toDto(p)).thenReturn(fireUrlsDto);

        List<FireDto> actual = fireService.getFire(address);


        assertEquals(expected.size(), actual.size());
    }

}
