package com.finalproject.BankApplication.controller;

import com.finalproject.BankApplication.model.Customer;
import com.finalproject.BankApplication.model.Loan;
import com.finalproject.BankApplication.service.AccountService;
import com.finalproject.BankApplication.service.CustomerService;
import com.finalproject.BankApplication.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/loandetails")
    public String showLoanDetails(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int cstId = customer.getId();
        int accountId = accountService.findAccountByCstId(cstId).getId();
        Loan loan = loanService.findLoanByAccountId(accountId);
        model.addAttribute("loan",loan);
        return "customer/loandetails";
    }

}
