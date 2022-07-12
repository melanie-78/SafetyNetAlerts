package com.openclassrooms.SafetyNetAlerts.service;

import com.openclassrooms.SafetyNetAlerts.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    void saveAll(List<Address> addressList);
    Address save(Address address);
    Address findByAddressLabel(String addressLabel);
}
