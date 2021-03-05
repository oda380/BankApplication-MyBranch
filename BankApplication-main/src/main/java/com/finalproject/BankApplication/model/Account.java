package com.finalproject.BankApplication.model;

import lombok.Data;

import javax.persistence.*;



@Entity
@Data
@Table
public class Account extends BaseEntity{

    @Column(name="account_number")
    private String accountNumber;

    @OneToOne(targetEntity = Customer.class)
    private Customer customer;

    @Column()
    private long balance;

    @OneToOne(targetEntity = Loan.class, cascade = CascadeType.ALL)
    private Loan loan;


}
