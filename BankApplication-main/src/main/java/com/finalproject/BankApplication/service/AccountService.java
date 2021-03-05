package com.finalproject.BankApplication.service;

import com.finalproject.BankApplication.model.Account;
import com.finalproject.BankApplication.model.Transaction;
import com.finalproject.BankApplication.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account findAccountById(int id){


        return accountRepository.findById(id).get();
    }

    public Account findAccountByCstId(int cstid){
        return accountRepository.findByCustomerId(cstid);
    }

    public boolean isClientOfTheBank(String accountNumber){

        return accountRepository.existsByAccountNumber(accountNumber);
    }

    public Account findAccountByAccountNumber( String accountNumber){

        return accountRepository.findByAccountNumber(accountNumber);
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public void updateCustomerAccount( Account newAccount){
        //transactionRepository.updateTransaction(id, amount);
        int id = newAccount.getId();
        Account account = accountRepository.findById(id).get();
        accountRepository.deleteById(id);
        //account.setBalance(balance);
        //account.setId(id);
        saveAccount(account);
    }
}

