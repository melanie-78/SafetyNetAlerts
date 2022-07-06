package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class )
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;

    @Mock
    AddressRepository addressRepository;

    @Test
    public void saveTest(){
        Address address = new Address(null, "1509 Culver St", "Culver", "97451", null, null);
        Address expected = address;
        when(addressRepository.save(address)).thenReturn(address);

        Address actual = addressService.save(address);

        assertEquals(expected.getLabel(), actual.getLabel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getZip(), actual.getZip());
    }

    @Test
    public void findByAddressLabelTest(){
        String label = "1509 Culver St";
        Address address = new Address(null, "1509 Culver St", "Culver", "97451", null, null);
        Address expected = address;
        when(addressRepository.findByLabel(label)).thenReturn(address);

        Address actual = addressService.findByAddressLabel(label);

        assertEquals(expected.getLabel(), actual.getLabel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getZip(), actual.getZip());
    }
}
