package com.finalproject.BankApplication.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Transaction extends TimeEntity{

    private String recipientAccount;
    private String recipientName;
    private String senderAccount;
    private String senderName;

    private long amount;
    private String reason;

    private long senderBalance;
    private long recipientBalance;

}
