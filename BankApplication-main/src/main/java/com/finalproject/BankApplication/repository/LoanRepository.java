package com.finalproject.BankApplication.repository;

import com.finalproject.BankApplication.model.Loan;
import org.hibernate.Criteria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer> {

    public Loan findByAccountId(int accountId);
}
