package com.finalproject.BankApplication.repository;

import com.finalproject.BankApplication.model.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {


    public List<Transaction> findByRecipientAccount(String recipient_account);
    public List<Transaction> findBySenderAccount(String sender_account);
    public List<Transaction> findByAmount(long amount);


    @Modifying(clearAutomatically = true)
    @Query("update Transaction u set u.amount = :amount where u.id = :id")
    void updateTransaction(@Param(value = "id") Integer id, @Param(value = "amount") long amount);


}
