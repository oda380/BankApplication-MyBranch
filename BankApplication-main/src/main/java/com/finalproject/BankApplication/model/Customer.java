package com.finalproject.BankApplication.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table
public class Customer extends BaseEntity{

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @OneToOne(targetEntity = Account.class)
    private Account account;

    private Date dateOfBirth;

    private Long annualIncome = 0L;

    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;

    @Column(name = "ACTIVE")
    private int active;

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
}

