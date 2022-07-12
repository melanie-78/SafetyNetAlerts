package com.openclassrooms.SafetyNetAlerts;

import com.openclassrooms.SafetyNetAlerts.Repository.PersonRepository;
import com.openclassrooms.SafetyNetAlerts.json.DataWrapper;
import com.openclassrooms.SafetyNetAlerts.json.ExtractionRunner;
import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.json.dto.MedicalRecordDto;
import com.openclassrooms.SafetyNetAlerts.json.dto.PersonDto;
import com.openclassrooms.SafetyNetAlerts.json.mapper.AddressMapper;
import com.openclassrooms.SafetyNetAlerts.json.mapper.MedicalRecordMapper;
import com.openclassrooms.SafetyNetAlerts.json.mapper.PersonMapper;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.ImplAddressService;
import com.openclassrooms.SafetyNetAlerts.service.ImplFireStationService;
import com.openclassrooms.SafetyNetAlerts.service.ImplMedicalRecordService;
import com.openclassrooms.SafetyNetAlerts.service.ImplPersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ExtractionRunnerTest {
    @InjectMocks
    private ExtractionRunner ex;
    @InjectMocks
    private AddressMapper addressMapper;
    @InjectMocks
    private MedicalRecordMapper medicalRecordMapper;

    @Mock
    PersonMapper personMapper;
    @Mock
    ImplPersonService personService;
    @Mock
    ImplAddressService addressService;
    @Mock
    ImplMedicalRecordService medicalRecordService;
    @Mock
    ImplFireStationService fireStationService;
    @Mock
    PersonRepository personRepository;

    @Test
    public void extractPersonsTest(){
        //GIVEN
        PersonDto p = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Address address = new Address(null, "1509 Culver St", "Culver", "97451",null,null);
        Person person = new Person(null, "John", "Boyd", address, "841-874-6512", "jaboyd@email.com", null);
        List<Person> pList = Arrays.asList(person);
        FireStationDto f = new FireStationDto("1509 Culver St", "3");
        MedicalRecordDto m = new MedicalRecordDto("John", "Boyd", "03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));
        DataWrapper dataWrapper = new DataWrapper(Arrays.asList(p), Arrays.asList(f), Arrays.asList(m));

        Map<Address, List<Person>> expected = new HashMap<Address, List<Person>>();
        expected.put(address, pList);

        when(addressService.save(address)).thenReturn(address);
        doNothing().when(personService).saveAll(pList);

        //WHEN
        ex.extractPersons(dataWrapper);

        //THEN
        verify(addressService, Mockito.times(1)).save(address);
        verify(personService, Mockito.times(1)).saveAll(pList);
    }

    //@Test
    public void extractMedicalRecordsTest(){
        //GIVEN
        PersonDto p = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Address address = new Address(null, "1509 Culver St", "Culver", "97451",null,null);
        Person person = new Person(null, "John", "Boyd", address, "841-874-6512", "jaboyd@email.com", null);
        FireStationDto f = new FireStationDto("1509 Culver St", "3");
        MedicalRecordDto m = new MedicalRecordDto("John", "Boyd", "03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));

        MedicalRecord medicalRecord = new MedicalRecord(null, "03/06/1984",Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"),null );
        medicalRecord.setPerson(person);

        List<MedicalRecord> mList = Arrays.asList(medicalRecord);
        person.setMedicalRecord(mList);

        DataWrapper dataWrapper = new DataWrapper(Arrays.asList(p), Arrays.asList(f), Arrays.asList(m));

        when(personService.findByFirstNameAndLastName(m.getFirstName(), m.getLastName())).thenReturn(person);
        when(medicalRecordMapper.toEntity(m)).thenReturn(medicalRecord);
        doNothing().when(medicalRecordService).saveAll(mList);


        //WHEN
        ex.extractMedicalRecords(dataWrapper);

        //THEN
        verify(medicalRecordMapper, Mockito.times(1)).toEntity(m);
        verify(medicalRecordService, Mockito.times(1)).saveAll(mList);
    }

    @Test
    public void extractFireStationsTest() {
        //GIVEN
        PersonDto p = new PersonDto("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Address address = new Address(null, "1509 Culver St", "Culver", "97451", null, null);
        FireStationDto f = new FireStationDto("1509 Culver St", "3");

        FireStation fireStation = new FireStation();
        fireStation.setStation(f.getStation());
        fireStation.setAddresses(Arrays.asList(address));

        List<FireStation> fList = Arrays.asList(fireStation);
        MedicalRecordDto m = new MedicalRecordDto("John", "Boyd", "03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));
        DataWrapper dataWrapper = new DataWrapper(Arrays.asList(p), Arrays.asList(f), Arrays.asList(m));


        when(addressService.findByAddressLabel(f.getAddress())).thenReturn(address);
        doNothing().when(fireStationService).saveAll(fList);

        //WHEN
        ex.extractFireStations(dataWrapper);

        //THEN
        verify(addressService, Mockito.times(1)).findByAddressLabel(f.getAddress());
        verify(fireStationService, Mockito.times(1)).saveAll(fList);
    }
}
