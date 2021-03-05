
package com.finalproject.BankApplication.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Data
@Table
public class Assessment extends TimeEntity{

    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private long annualIncome=0;
    private int firstDeposit=0;
    private String country="";
    private String city="";
    private String postcode="";
    private String street="";
    private int payDay=1;
    private String reason="";
    private long amount=0;
    private int customerId;
    private Date startDate;
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    private AssessmentType type;

    @Enumerated(EnumType.STRING)
    private AssessmentStatus status;

    @Enumerated(EnumType.STRING)
    private Decision decision;

    @Builder
    public void setDateOfBirth(String dateOfBirth) {
        Date d = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            d = format.parse(dateOfBirth);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        this.dateOfBirth = d;
    }

    @Builder
    public void setStartDate(String dateOfBirth) {
        Date d = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            d = format.parse(dateOfBirth);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        this.startDate = d;
    }

    @Builder
    public void setDueDate(String dateOfBirth) {
        Date d = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            d = format.parse(dateOfBirth);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        this.dueDate = d;
    }

}