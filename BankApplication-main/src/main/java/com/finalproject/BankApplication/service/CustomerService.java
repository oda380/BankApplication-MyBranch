package com.finalproject.BankApplication.service;
import com.finalproject.BankApplication.model.Customer;

import java.util.Optional;

public interface CustomerService {
    public Customer findUserByEmail(String email) ;
    public Customer saveUser(Customer customer);
    public Customer findUserById(int id);

}
