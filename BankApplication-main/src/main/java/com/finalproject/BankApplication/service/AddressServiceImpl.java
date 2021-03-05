package com.finalproject.BankApplication.service;

import com.finalproject.BankApplication.model.Address;
import com.finalproject.BankApplication.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Override
    @Transactional
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}