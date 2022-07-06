package com.openclassrooms.SafetyNetAlerts.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.json.mapper.AddressMapper;
import com.openclassrooms.SafetyNetAlerts.json.mapper.MedicalRecordMapper;
import com.openclassrooms.SafetyNetAlerts.json.mapper.PersonMapper;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.model.MedicalRecord;
import com.openclassrooms.SafetyNetAlerts.model.Person;
import com.openclassrooms.SafetyNetAlerts.service.AddressService;
import com.openclassrooms.SafetyNetAlerts.service.FireStationService;
import com.openclassrooms.SafetyNetAlerts.service.MedicalRecordService;
import com.openclassrooms.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Component
public class ExtractionRunner implements CommandLineRunner{
    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //read json and write to H2 db
        extractJsonData();
    }

    public void extractJsonData() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<DataWrapper> typeReference = new TypeReference<DataWrapper>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");

        try {
            DataWrapper dataWrapper = mapper.readValue(inputStream, typeReference);

            extractPersons(dataWrapper);
            extractMedicalRecords(dataWrapper);
            extractFireStations(dataWrapper);

        } catch (IOException e) {
            System.out.println("Unable to save persons: " + e.getMessage());
        }
    }

    public void extractPersons(DataWrapper dataWrapper){
        //Save persons
        Map<Address, List<Person>> addressListMap = dataWrapper.getPersons().stream()
                .map(personDto -> personMapper.toEntity(personDto, addressMapper))
                .collect(groupingBy(person -> person.getAddress()));
        for (Map.Entry<Address, List<Person>> entry : addressListMap.entrySet()) {
            Address address = entry.getKey();
            Address savedAddress = addressService.save(address);
            List<Person> values = entry.getValue();
            values.forEach(person -> person.setAddress(savedAddress));
            personService.saveAll(values);
        }
        System.out.println("Persons saved !");
    }
    public void extractMedicalRecords(DataWrapper dataWrapper){
        //Save medicalRecords
        List<MedicalRecord> medicalRecordList = dataWrapper.getMedicalRecords()
                .stream().map(medicalRecordDto -> medicalRecordMapper.toEntity(medicalRecordDto))
                .collect(Collectors.toList());
        medicalRecordService.saveAll(medicalRecordList);
        System.out.println("MedicalRecords saved !");

    }
    public void extractFireStations(DataWrapper dataWrapper) {
        //Save fireStations
        List<FireStation> fireStationResult = new ArrayList<>();
        Map<String, List<FireStationDto>> listMap = dataWrapper.getFireStations()
                .stream().collect(groupingBy(fireStationDto -> fireStationDto.getStation()));

        for (Map.Entry<String, List<FireStationDto>> entry : listMap.entrySet()) {
            FireStation fireStation = new FireStation();

            String station = entry.getKey();
            fireStation.setStation(station);
            List<FireStationDto> values = entry.getValue();
            List<Address> addresses = values.stream().map(fireStationDto -> {
                String label = fireStationDto.getAddress();
                Address byAddressLabel = addressService.findByAddressLabel(label);
                return byAddressLabel;
            }).collect(Collectors.toList());

            fireStation.setAddresses(addresses);
            fireStationResult.add(fireStation);
        }
        fireStationService.saveAll(fireStationResult);
        System.out.println("FireStations saved !");
    }
}