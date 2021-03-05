package com.finalproject.BankApplication.controller;

import com.finalproject.BankApplication.model.Assessment;
import com.finalproject.BankApplication.model.Customer;
import com.finalproject.BankApplication.service.AssessmentService;
import com.finalproject.BankApplication.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {

    CustomerService customerService;
    AssessmentService assessmentService;


    @GetMapping("/customer/openLoan")
    public String loggedLoan(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        Assessment assessment = new Assessment();
        assessment.setFirstName(customer.getFirstName());
        assessment.setLastName(customer.getLastName());
        assessment.setEmail(customer.getEmail());
        model.addAttribute("assessment",assessment);
        return "customer/openLoan";
    }

    @PostMapping("/customer/openLoan")
    public String createLoggedLoan(@ModelAttribute Assessment assessment, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        assessment.setCustomerId(customer.getId());
        customer.setAnnualIncome(assessment.getAnnualIncome());
        assessmentService.saveNew(assessment);
        int id = assessmentService.findLastId();
        assessmentService.submit(id);
        assessmentService.loanType(id);
        String ref = "L" + (12346789 + id);
        model.addAttribute("confirmation","Your loan request has been submitted with reference " + ref );
        return "SubmitApplicationConfirmation";
    }
}
