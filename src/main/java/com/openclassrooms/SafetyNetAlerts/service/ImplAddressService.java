package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.Repository.AddressRepository;
import com.openclassrooms.SafetyNetAlerts.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplAddressService implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void saveAll(List<Address> addressList) {
        addressRepository.saveAll(addressList);
    }

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    public Address findByAddressLabel(String addressLabel) {
        return this.addressRepository.findByLabel(addressLabel);
    }
}
