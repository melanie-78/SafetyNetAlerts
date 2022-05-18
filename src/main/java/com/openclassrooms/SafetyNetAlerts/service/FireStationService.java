package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.Repository.FireStationRepository;
import com.openclassrooms.SafetyNetAlerts.json.dto.FireStationDto;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import com.openclassrooms.SafetyNetAlerts.model.FireStation;
import com.openclassrooms.SafetyNetAlerts.web.dto.FireStationUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FireStationService {
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private AddressRepository addressRepository;

    public void saveAll(List<FireStation> fireStationList){
        fireStationRepository.saveAll(fireStationList);
    }
    

    public void saveFireStation(FireStationDto fireStationDto) {

        FireStation fireStation = fireStationRepository.findByStation(fireStationDto.getStation());
        String address = fireStationDto.getAddress();
        Address byAddressLabel = addressRepository.findByLabel(address);

        if(fireStation == null) {
            FireStation fireStationNew = new FireStation();
            fireStationNew.setStation(fireStationDto.getStation());
            List<Address> addressesNew = new ArrayList<>();
            addressesNew.add(byAddressLabel);
            fireStationNew.setAddresses(addressesNew);
            fireStationRepository.save(fireStationNew);
        }else{
            Collection<Address> addresses = fireStation.getAddresses();
            addresses.add(byAddressLabel);
            fireStation.setAddresses(addresses);
            fireStationRepository.save(fireStation);
        }
    }

    public void deleteFireStation (FireStationDto fireStationDto){
        FireStation fireStation = fireStationRepository.findByStation(fireStationDto.getStation());

        if(fireStation == null){
            throw new NoSuchElementException("This fireStation does'nt exist in H2 database, we need an existing FireStation");
        } else{
            Address byLabel = addressRepository.findByLabel(fireStationDto.getAddress());
            if(byLabel == null){
                throw new NoSuchElementException("This address does'nt exist in H2 database, we need an existing address");
            }else {
                fireStation.getAddresses().remove(byLabel);
                fireStationRepository.save(fireStation);
            }
        }

    }

    public void updateFireStation(FireStationUpdateDto fireStationUpdateDto) {

        FireStation fireStation = fireStationRepository.findByStation(fireStationUpdateDto.getStation());
        Address byLabel= addressRepository.findByLabel(fireStationUpdateDto.getAddress());

        fireStation.getAddresses().remove(byLabel);
        fireStation.setAddresses(fireStation.getAddresses());
        fireStationRepository.save(fireStation);

        FireStation fireStationUpdate = fireStationRepository.findByStation(fireStationUpdateDto.getNewStation());

        if(fireStationUpdate == null){
            FireStation fireStation2 = new FireStation();
            fireStation2.setStation(fireStationUpdateDto.getNewStation());

            List<Address> addressesNew= new ArrayList<>();
            addressesNew.add(byLabel);
            fireStation2.setAddresses(addressesNew);

            fireStationRepository.save(fireStation2);
        } else {
            fireStationUpdate.getAddresses().add(byLabel);
            fireStationRepository.save(fireStationUpdate);
        }
    }
}
