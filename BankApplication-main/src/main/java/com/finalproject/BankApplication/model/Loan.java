package com.finalproject.BankApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Loan extends TimeEntity{

    private long amount;
    private String reason;

    @OneToOne(targetEntity = Account.class)
    private Account account;

    private Date startDate;
    private Date dueDate;
    private int payDay;

}
