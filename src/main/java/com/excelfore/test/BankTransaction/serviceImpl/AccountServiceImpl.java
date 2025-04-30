package com.excelfore.test.BankTransaction.serviceImpl;

import com.excelfore.test.BankTransaction.model.Account;
import com.excelfore.test.BankTransaction.repository.AccountRepository;
import com.excelfore.test.BankTransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    public AccountRepository accrepo;

    @Override
    public Account createAccount(Account account) {
        return accrepo.save(account);
    }

    @Override
    public List<Account> getAllAccount() {
        return accrepo.findAll();
    }

    @Override
    public Optional<Account> getSingleAccount(Long id) {
        return accrepo.findById(id);
    }

    @Override
    public Account deposit(long id, double amount) {
        Account account = getSingleAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return accrepo.save(account);
    }

    @Override
    public Account withdraw(long id, double amount) {
        Account account = getSingleAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accrepo.save(account);
    }
}
