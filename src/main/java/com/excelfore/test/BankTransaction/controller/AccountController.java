package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.exception.AccountNotFoundException;
import com.excelfore.test.BankTransaction.model.Account;
import com.excelfore.test.BankTransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accSer;

    @PostMapping
    public Account createAccount(@RequestBody Account account){
        System.out.println("AC-1");
        return accSer.createAccount(account);
    }

    @GetMapping("/single-account/{id}")
    public Optional<Account> getSingleAccount(@PathVariable Long id){
        return Optional.ofNullable(
                accSer.getSingleAccount(id)
                        .orElseThrow(() -> new AccountNotFoundException("Account "+id+" not found"))
        );
    }

    @GetMapping("/all")
    public List<Account> getAllAccount(){
        return accSer.getAllAccount();
    }

    @PutMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return accSer.deposit(id, amount);
    }

    @PutMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return accSer.withdraw(id, amount);
    }

    @GetMapping
    public String myTest(){
        return "its working string";
    }
}
