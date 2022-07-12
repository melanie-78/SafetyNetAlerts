package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
    @InjectMocks
    private ImplFireStationService fs;

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    AddressRepository addressRepository;

   @Test
    public void saveFireStationExistingStationTest(){
        //GIVEN
        FireStationDto fireStationDto = new FireStationDto("112 Steppes Pl", "4");

        Address a = new Address();
        a.setLabel("1509 Culver St");
        a.setCity("Culver");
        a.setZip("97451");

        FireStation fireStation = new FireStation();
        fireStation.setStation(fireStationDto.getStation());
        fireStation.setAddresses(new ArrayList<>());

        a.setFireStations(Arrays.asList(fireStation));


        when(fireStationRepository.findByStation("4")).thenReturn(fireStation);
        when(addressRepository.findByLabel("112 Steppes Pl")).thenReturn(a);

        //WHEN
        fs.saveFireStation(fireStationDto);

        //THEN
        verify(fireStationRepository, Mockito.times(1)).save(fireStation);
        assertEquals(1, fireStation.getAddresses().size());

    }

    @Test
    public void saveFireStationNotExistingStationTest(){
        //GIVEN
        FireStationDto fireStationDto = new FireStationDto("112 Steppes Pl", "4");
        when(fireStationRepository.findByStation(any(String.class))).thenReturn(null);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(any(Address.class));

        //WHEN
        fs.saveFireStation(fireStationDto);

        //THEN
        verify(fireStationRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateFireStationExistingTest(){
       // GIVEN :
        FireStationUpdateDto fireStationUpdateDto = new FireStationUpdateDto("112 Steppes Pl", "4", "1");
        FireStation fireStation =new FireStation();
        FireStation fireStationUpdate = new FireStation();
        Address address = new Address();
        address.setLabel("112 Steppes Pl");
        fireStation.getAddresses().add(address);
        when(fireStationRepository.findByStation("4")).thenReturn(fireStation);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(address);
        when(fireStationRepository.findByStation("1")).thenReturn(fireStationUpdate);

        fs.updateFireStation(fireStationUpdateDto);

        // THEN : l'adresse n'est plus liée à la firestation 4 mais à la firestation 1
        assertEquals(0, fireStation.getAddresses().size());
        assertEquals(1, fireStationUpdate.getAddresses().size());
    }

    @Test
    public void updateFireStationNotExistingTest(){
        FireStationUpdateDto fireStationUpdateDto = new FireStationUpdateDto("112 Steppes Pl", "4", "1");
        FireStation fireStation =new FireStation();

        Address address = new Address();
        address.setLabel("112 Steppes Pl");
        fireStation.getAddresses().add(address);
        FireStation expected = new FireStation();
        expected.setStation("1");
        expected.getAddresses().add(address);

        when(fireStationRepository.findByStation("4")).thenReturn(fireStation);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(address);
        when(fireStationRepository.findByStation("1")).thenReturn(null);

        fs.updateFireStation(fireStationUpdateDto);

        assertEquals(1, expected.getAddresses().size());
    }

   @Test
    public void deleteFireStationThrowsExceptionTest(){
        FireStationDto fireStationDto = new FireStationDto("112 Steppes Pl", "4");
        when(fireStationRepository.findByStation(any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->fs.deleteFireStation(fireStationDto));

        verify(fireStationRepository,Mockito.times(1)).findByStation(any(String.class));
    }

   @Test
    public void deleteFireStationNotExistingThrowsExceptionTest() {
        FireStationDto fireStationDto = new FireStationDto("112 Steppes Pl", "4");
        FireStation fireStation = new FireStation();
        when(fireStationRepository.findByStation(any(String.class))).thenReturn(fireStation);
        when(addressRepository.findByLabel(any(String.class))).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> fs.deleteFireStation(fireStationDto));

        verify(fireStationRepository, Mockito.times(1)).findByStation(any(String.class));
        verify(addressRepository, Mockito.times(1)).findByLabel(any(String.class));
    }

    @Test
    public void deleteFireStationNotAddressExistingTest() {
        FireStationDto fireStationDto = new FireStationDto("112 Steppes Pl", "4");
        FireStation f = new FireStation();
        f.setStation("4");


        when(fireStationRepository.findByStation("4")).thenReturn(f);
        when(addressRepository.findByLabel("112 Steppes Pl")).thenReturn(null);

        assertThrows(NoSuchElementException.class, ()->fs.deleteFireStation(fireStationDto));

    }

    @Test
    public void deleteFireStationTest() {
        FireStationDto fireStationDto = new FireStationDto("112 Steppes Pl", "4");
        Address address = new Address();
        address.setLabel("112 Steppes Pl");
        FireStation f = new FireStation();
        f.setStation("4");
        f.getAddresses().add(address);

        when(fireStationRepository.findByStation("4")).thenReturn(f);
        when(addressRepository.findByLabel("112 Steppes Pl")).thenReturn(address);

        fs.deleteFireStation(fireStationDto);

        assertEquals(0, f.getAddresses().size());
    }
}
