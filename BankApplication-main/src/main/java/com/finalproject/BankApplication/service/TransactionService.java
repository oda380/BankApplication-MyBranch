package com.finalproject.BankApplication.service;

import com.finalproject.BankApplication.model.Transaction;
import com.finalproject.BankApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(){
        return (List<Transaction>) transactionRepository.findAll();
    }


    public List<Transaction> getTransactionsByAmount(long amount){
        List<Transaction> transactions = new ArrayList<>();
        System.out.println("Sender");
        //transactions = transactionRepository.findByAmount(amount);
        transactionRepository.findByAmount(amount).forEach(transactions::add);
        return  transactions;
    }

    //outcome
    public List<Transaction> getTransactionsBySender(String account_number){
        List<Transaction> transactions = new ArrayList<>();
        System.out.println("Sender");
        transactions = transactionRepository.findBySenderAccount(account_number);
        return  transactions;
    }
    //INCOME
    public List<Transaction> getTransactionsByRecipient(String account_number){
        List<Transaction> transactions = new ArrayList<>();
        transactions =  transactionRepository.findByRecipientAccount(account_number);
        return transactions;
    }


    public List<Transaction> getAllUserTransactions(String account_number){
        List<Transaction> allTransactions = new ArrayList<>();
        System.out.println("Sent and received");
        for (Transaction transaction :  transactionRepository.findAll()){
            if (transaction.getRecipientAccount().equals(account_number) || transaction.getSenderAccount().equals(account_number) ){
                allTransactions.add(transaction);
            }
        }
        return  allTransactions;
    }

    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public void updateCustomerTransaction( Integer id, long amount){
        //transactionRepository.updateTransaction(id, amount);
        Transaction transaction = transactionRepository.findById(id).get();
        transactionRepository.deleteById(id);
        transaction.setAmount(amount);
        transaction.setId(id);
        saveTransaction(transaction);
    }







}
