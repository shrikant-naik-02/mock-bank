package com.excelfore.test.BankTransaction.service;

import com.excelfore.test.BankTransaction.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    public Account createAccount(Account account);

    public List<Account> getAllAccount();

    public Optional<Account> getSingleAccount(Long id);

    public Account deposit(long id, double amount);

    public Account withdraw(long id, double amount);

}
