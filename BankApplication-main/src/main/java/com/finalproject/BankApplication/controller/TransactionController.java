package com.finalproject.BankApplication.controller;
import com.finalproject.BankApplication.model.Account;
import com.finalproject.BankApplication.model.Customer;
import com.finalproject.BankApplication.model.Loan;
import com.finalproject.BankApplication.model.Transaction;
import com.finalproject.BankApplication.service.AccountService;
import com.finalproject.BankApplication.service.CustomerServiceImpl;
import com.finalproject.BankApplication.service.LoanService;
import com.finalproject.BankApplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/customer")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;

    //TODO: Modify the mapping for the customerdashboard to properly get data
    // from other classes without throwing null pointer exceptions when not all classes are present.

  /*@GetMapping ("/customerDashboard")
    public String  blabla(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int id=customer.getId();
        System.out.println("oh oh  this is a customer ---------------------------:" );

        //////////////////// new users don't have Account & Loan : throws null pointer exception ///////////////////////////
        Account account = accountService.findAccountByCstId(id);
        Loan loan = loanService.findLoanByAccountId(id);
        System.out.println(account.toString());
        System.out.println(loan.toString());
        if ((account == null) && (loan == null)){
            model.addAttribute("accoutn", "none");
            model.addAttribute("loan", "none");
        }
        else if ((account != null) && (loan == null)){
            model.addAttribute("account",account);
        }
        else if ((account == null) && (loan != null)){
        loan.setReason("this reason");
        model.addAttribute("loan", loan);
        }

        return "customer/customerDashboard";
    }*/

    @GetMapping(value = "/transaction")
    public String getTransactionsPages() {
        System.out.println("I am in transaction");
        return "customer/transaction";
    }
    @GetMapping("/maketransfer")
    public String initiateTransfer(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int cstId = customer.getId();
        System.out.println("here is the id : "+ customer.getId());
        //int id = 2;
        Account senderAccount = accountService.findAccountById(cstId);
        Transaction transaction = new Transaction();
        model.addAttribute("transaction", transaction);
        model.addAttribute("cst",customer);
        model.addAttribute("acc",senderAccount);
        return "customer/transfers";
    }
    @PostMapping("/maketransfer")
    public String validateTransfer(@ModelAttribute Transaction transaction, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int cstId = customer.getId();
        System.out.println("transfer initiated ......................");
        //int id =2;
        //Customer customer = customerService.findUserById(cstId);
        Account account = accountService.findAccountByCstId(cstId);
        transaction.setSenderName(customer.getFirstName() +" "+ customer.getLastName());
        transaction.setSenderAccount(accountService.findAccountById(cstId).getAccountNumber());
        // end of mocking
        String validationMessage =" You transfer of " + transaction.getAmount() + " to " + transaction.getRecipientName() + " has been ";;
        // implement the verification of balance.
        long currentBalance = account.getBalance();
        if ( transaction.getAmount() < currentBalance ){
            System.out.println(" transfer accepted !!!!!! ");
            validationMessage += " Validated";
            currentBalance -= transaction.getAmount();
            account.setBalance(currentBalance);
            System.out.println("sender balance updated ");
            transaction.setSenderBalance(currentBalance); // we save the new balance for the sender
            if (accountService.isClientOfTheBank(transaction.getRecipientAccount())){
                System.out.println(" the receiver is our client is our client");
                Account receiverAccount = new Account();
                receiverAccount = accountService.findAccountByAccountNumber(transaction.getRecipientAccount());
                long receiverBalance = receiverAccount.getBalance();
                receiverBalance += transaction.getAmount();         // calculating new balance.
                System.out.println("receiver balance updated ");
                receiverAccount.setBalance(receiverBalance);        //updating receiver balance
                transaction.setRecipientBalance(receiverBalance); // we save the new balance for the sender
                //accountService.saveAccount(receiverAccount);
            }else{
                transaction.setRecipientBalance(0);
            }
            transactionService.saveTransaction(transaction);
        }else{
            validationMessage += " rejected. You can not transfer more than you have";
        }
        //implementing sender and receiver balance after transfer
        model.addAttribute("validationMessage",validationMessage);
        model.addAttribute("transaction", transaction);
        return "customer/transferstatus";
    }
    @GetMapping("/transactions/sent")
    public String getTransactionsBySender( Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int cstId = customer.getId();
        String accountNumber = accountService.findAccountByCstId(cstId).getAccountNumber();
        //int id =1;
        List<Transaction> transactions = transactionService.getTransactionsBySender(accountService.findAccountByCstId(cstId).getAccountNumber());
        System.out.println("we found this many transactions sent"+transactions.size());
        String transferMessage = transactions.size()==0? "You have not made any transaction": " here is your list of sent transactions";
        List<Long> balances = new ArrayList<>();
        for (Transaction transaction :transactions){
            balances.add(transaction.getSenderBalance());
        }
        model.addAttribute("balances", balances);
        //display in terminal for test
        System.out.println("Showing all transactions Sent ");
        for (Transaction transaction :transactions) {
            System.out.println(transaction.getId() + " created_at" + transaction.getCreatedAt()+" modified_at"+transaction.getModifiedAt());
            System.out.println(transaction.toString());
        }
        model.addAttribute("transactions", transactions);
        model.addAttribute("transferMessage", transferMessage);
        model.addAttribute("acountNumber",accountNumber);
        return "customer/transactions";
    }
    @GetMapping("/transactions/received")
    public String getTransactionsByRecipient(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int cstId = customer.getId();
        String accountNumber = accountService.findAccountByCstId(cstId).getAccountNumber();
        List<Transaction> transactions = transactionService.getTransactionsByRecipient(accountService.findAccountByCstId(cstId).getAccountNumber());
        System.out.println("we found this many transactions received"+transactions.size());
        String transferMessage = transactions.size()==0? "You have not received any transactions": " here is your list of all received transactions";
        List<Long> balances = new ArrayList<>();
        for (Transaction transaction :transactions){
            balances.add(transaction.getRecipientBalance());
        }
        model.addAttribute("balances", balances);
        model.addAttribute("balanceType","received");
        //display in terminal for test
        System.out.println("Showing all transactions received ");
        for (Transaction transaction :transactions) {
            System.out.println(transaction.getId() + " created_at" + transaction.getCreatedAt()+" modified_at"+transaction.getModifiedAt());
            System.out.println(transaction.toString());
        }
        model.addAttribute("transactions", transactions);
        model.addAttribute("transferMessage", transferMessage);
        model.addAttribute("accountNumber",accountNumber);
        return "customer/transactions";
    }
    @GetMapping(value = {"/transactions", "/transactions/all"})
    public String getAllTransactions(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findUserByEmail(auth.getName());
        int cstId = customer.getId();
        //int id =1;
        String accountNumber = accountService.findAccountByCstId(cstId).getAccountNumber();
        System.out.println("Showing all transactions");
        //List<Transaction> transactions = transactionService.getAllTransactions();
        //List<Transaction> allTransactions = transactionService.getTransactionsByRecipient(accountNumber);
        //allTransactions.addAll( transactionService.getTransactionsBySender(accountNumber) ) ;
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        List<Transaction> allTransactionsForCst = new ArrayList<>();
        for(Transaction transaction :allTransactions){
            if ( (transaction.getSenderAccount()==accountNumber) || transaction.getRecipientAccount()==accountNumber){
                allTransactionsForCst.add(transaction);
            }
        }
        String transferMessage = allTransactions.size()==0? "You have no transactions": " here is your list of all transactions";
        for (Transaction transaction :allTransactions) {
            System.out.println(transaction.getId() + " created_at" + transaction.getCreatedAt()+" modified_at"+transaction.getModifiedAt());
            System.out.println(transaction.toString());
        }
        /*Comparator byDate = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
            public int compare(Transaction c1, Transaction c2) {
                return Long.valueOf(c1.getCreatedAt().getTime()).compareTo(c2.getCreatedAt().getTime());
            }
        };
        Collections.sort(allTransactions,byDate);
        List<Long> balances = new ArrayList<>();
        for (Transaction transaction :allTransactions){
            balances.add(transaction.getSenderBalance());
        }*/
        model.addAttribute("accountNumber",accountNumber);
        model.addAttribute("transactions",allTransactionsForCst);
        model.addAttribute("transferMessage", transferMessage);
        return "customer/transactions";
    }
}