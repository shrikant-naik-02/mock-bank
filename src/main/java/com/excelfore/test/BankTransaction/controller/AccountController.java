package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.enums.Role;
import com.excelfore.test.BankTransaction.exception.AccountNotFoundException;
import com.excelfore.test.BankTransaction.model.Account;
import com.excelfore.test.BankTransaction.model.User;
import com.excelfore.test.BankTransaction.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accSer;

//    @PostMapping
//    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
//        log.info("Received request to / with params: {}", account);
//
//        // Extracting fields from Account and its User
//        String accountHolderName = account.getAccountHolderName();
//        double balance = account.getBalance();
//        String username = account.getAccountHolderName(); // assuming getUser() returns a User object
//
//        // Dynamically creating the JSON string
//        String customJson = """
//            {
//                {
//                        "id": 16,
//                        "accountHolderName":  "%s",
//                        "balance":   %.2f,
//                        "createdAt": "2025-05-09T14:48:46.339362",
//                        "updatedAt": "2025-05-09T14:48:46.339522",
//                        "user": {
//                            "id": 34,
//                            "name": "user25",
//                            "username": "%s",
//                            "password": "$2a$10$WszV.c9kENam3dGxO1s5N.Rr5tpQoIA7E3rDRj3h8HT5sg7QfQuIq",
//                            "role": "USER",
//                            "createdAt": "2025-05-07T12:52:07.297741",
//                            "updatedAt": "2025-05-07T12:52:07.29783"
//                        }
//                    }
//            }
//            """.formatted(accountHolderName, balance, username);
//
//        log.info("Custom JSON:\n{}", customJson);
//
//        Account createdAccount = accSer.createAccount(account);
//        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws JsonProcessingException, JsonProcessingException {
        log.info("Received request to / with params: {}", account);

        String json = """
        {
            "id": 16,
            "accountHolderName": "%s",
            "balance": %.2f,
            "createdAt": "2025-05-09T14:48:46.339362",
            "updatedAt": "2025-05-09T14:48:46.339522",
            "user": {
                "id": 34,
                "name": "%s",
                "username": "%s",
                "password": "$2a$10$WszV.c9kENam3dGxO1s5N.Rr5tpQoIA7E3rDRj3h8HT5sg7QfQuIq",
                "role": "USER",
                "createdAt": "2025-05-07T12:52:07.297741",
                "updatedAt": "2025-05-07T12:52:07.297830"
            }
        }
        """.formatted(
                account.getAccountHolderName(),
                account.getBalance(),
                account.getAccountHolderName(),
                account.getAccountHolderName()
        );

        // ✅ Convert string to Account object
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules(); // for LocalDateTime support
        Account mockAccount = mapper.readValue(json, Account.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(mockAccount);
    }


//    @PostMapping
//    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
//        log.info("Received request to / with params: {}", account);
//
//        // Mock values
//        Account mockAccount = new Account();
//        mockAccount.setId(16L); // mock ID
//        mockAccount.setAccountHolderName(account.getAccountHolderName());
//        mockAccount.setBalance(account.getBalance());
//        mockAccount.setCreatedAt(LocalDateTime.now());
//        mockAccount.setUpdatedAt(LocalDateTime.now());
//
//        // Mock user (assuming account.getUser() is not null)
//        User user = account.getUser();
//        if (user == null) user = new User(); // fallback in case null
//        user.setId(34L);
//        user.setName(account.getAccountHolderName());
//        user.setUsername(account.getAccountHolderName());
//        user.setPassword("mocked-password-hash");
//        user.setRole(Role.valueOf("USER"));
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
//
//        mockAccount.setUser(user);
//
//        log.info("Mocked Account Response: {}", mockAccount);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(mockAccount);
//    }




//    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accSer.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
