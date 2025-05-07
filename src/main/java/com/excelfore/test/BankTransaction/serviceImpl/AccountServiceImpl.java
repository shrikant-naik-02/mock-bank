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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        String currentUsername2 = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Object currentUsername3 = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String currentUsername4 = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getClass());
        Object currentUsername5 = SecurityContextHolder.getContext().getAuthentication().getDetails();
        Object currentUsername6 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        System.out.println(currentUsername);
//        System.out.println(currentUsername2);
//        System.out.println(currentUsername3);
//        System.out.println(currentUsername4);
//        System.out.println(currentUsername5);
//        System.out.println(currentUsername6);

//        currentUserName variables value represent the that value which is coming from basic auth login and going to check that value with account username which is unique
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

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
        accountRepository.delete(account);
    }


//    It will Work well if SecurityConfig get disable
//    @Override
//    public void deleteAccount(Long id) {
//        // from basic auth we are fetching username here
//        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("DEl-Acc-1"+currentUsername);
//
////        this have vaule like [ROLE_USER]
//        Collection<? extends GrantedAuthority> authorities =
//                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        System.out.println("DEl-Acc-2"+authorities);
//
//        // checking the value is ROLE_ADMIN or not
//        boolean isAdmin = authorities.stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//        System.out.println("DEl-Acc-3"+isAdmin);
//        if (!isAdmin) {
//            throw new AccessDeniedException("Only admins are allowed to delete accounts.");
//        }
//        System.out.println("DEl-Acc-4");
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
//        System.out.println("DEl-Acc-5"+account);
//        accountRepository.delete(account);
//    }

}
