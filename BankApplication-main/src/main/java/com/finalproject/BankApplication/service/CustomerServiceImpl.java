package com.finalproject.BankApplication.service;

import com.finalproject.BankApplication.model.Customer;
import com.finalproject.BankApplication.model.Role;
import com.finalproject.BankApplication.repository.CustomerRepository;
import com.finalproject.BankApplication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Customer findUserByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer saveUser(Customer customer) {
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customer.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        customer.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return customerRepository.save(customer);
    }

    @Override
    public Customer findUserById(int id){
        return customerRepository.findById(id).get();

    }
}





