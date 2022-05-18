package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public void saveAll(List<Address> addressList){
        addressRepository.saveAll(addressList);
    }

    public Address save(Address address) {
        return this.addressRepository.save(address);
    }

    public Address findByAddressLabel(String addressLabel){
        return this.addressRepository.findByLabel(addressLabel);
    }
}
