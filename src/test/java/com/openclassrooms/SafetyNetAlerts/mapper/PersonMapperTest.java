package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.json.mapper.AddressMapper;
import com.openclassrooms.SafetyNetAlerts.json.mapper.PersonMapper;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class PersonMapperTest {
    @InjectMocks
    private PersonMapper personMapper;

    @InjectMocks
    AddressMapper addressMapper;

   @Test
    public void toEntityTest(){
        PersonDto personDto = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person expected = new Person (null, "John", "Boyd", new Address(null, "1509 Culver St", "Culver", "97451",null,null),"841-874-6512", "jaboyd@email.com",null);

        Person actual = personMapper.toEntity(personDto, addressMapper);

        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

}
