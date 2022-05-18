package com.openclassrooms.SafetyNetAlerts.mapper;

import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.json.mapper.AddressMapper;
import com.openclassrooms.SafetyNetAlerts.json.mapper.PersonMapper;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AddressMapperTest {
    @InjectMocks
    private AddressMapper addressMapper;

    @Test
    public void toEntityTest() {

        PersonDto personDto = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Address expected = new Address(null, "1509 Culver St", "Culver", "97451", null, null);

        Address actual = addressMapper.toEntity(personDto);

        assertEquals(expected.getLabel(), actual.getLabel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getZip(), actual.getZip());
    }
}
