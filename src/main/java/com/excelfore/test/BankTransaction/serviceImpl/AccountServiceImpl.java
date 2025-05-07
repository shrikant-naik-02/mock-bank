package com.excelfore.test.BankTransaction.serviceImpl;

import com.excelfore.test.BankTransaction.exception.AccountNotFoundException;
import com.excelfore.test.BankTransaction.exception.InsufficientFundsException;
import com.excelfore.test.BankTransaction.exception.UserAlreadyHasAccountException;
import com.excelfore.test.BankTransaction.exception.UserNotFoundException;
import com.excelfore.test.BankTransaction.model.Account;
import com.excelfore.test.BankTransaction.model.User;
import com.excelfore.test.BankTransaction.repository.AccountRepository;
import com.excelfore.test.BankTransaction.repository.UserRepository;
import com.excelfore.test.BankTransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public Account createAccount(Account account) {
        String username = account.getAccountHolderName();

        // 1. Find username Value With accountHoldername
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user found with username: " + username));

        // 2. Check if the user already has an account.
        if (accountRepository.findByUser(user).isPresent()) {
            throw new UserAlreadyHasAccountException("User already has an account");
        }

        // 3. Assign and save
        account.setUser(user);
        return accountRepository.save(account);
    }


    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getSingleAccount(Long id) {
        return accountRepository.findById(id);
    }


    private void authorizeUser(Account account) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!account.getUser().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You are not authorized to perform this action.");
        }
    }


    @Override
    public Account deposit(long id, double amount) {
        Account account = getSingleAccount(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        authorizeUser(account);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }


    @Override
    public Account withdraw(long id, double amount) {
        Account account = getSingleAccount(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        authorizeUser(account);

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }
}
