package com.finalproject.BankApplication.service;

import com.finalproject.BankApplication.model.Loan;
import com.finalproject.BankApplication.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    public Loan findLoanByAccountId(int accountId){

        return loanRepository.findByAccountId(accountId);
    }
}
