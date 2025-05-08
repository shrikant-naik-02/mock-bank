package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.enums.Role;
import com.excelfore.test.BankTransaction.exception.AccountNotFoundException;
import com.excelfore.test.BankTransaction.exception.UserNotFoundException;
import com.excelfore.test.BankTransaction.model.Account;
import com.excelfore.test.BankTransaction.model.User;
import com.excelfore.test.BankTransaction.service.AccountService;
import com.excelfore.test.BankTransaction.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private Account a1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        a1 = new Account();
        a1.setId(1L);
        a1.setBalance(1000.0);
        a1.setAccountHolderName("this name is present in db");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAccount_shouldReturnCreatedResponse() {
        when(accountService.createAccount(a1)).thenReturn(a1);

        ResponseEntity<Account> response = accountController.createAccount(a1);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(a1, response.getBody());
    }

    @Test
    void createAccount_shouldReturnBadRequest_whenBodyIsNull() {
        a1 = null;
        ResponseEntity<Account> response = accountController.createAccount(a1);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void createAccount_shouldReturnForbidden_whenUserNotAuthorized() {
        when(accountService.createAccount(a1))
                .thenThrow(new AccessDeniedException("Forbidden"));

        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> accountController.createAccount(a1)
        );
        assertEquals("Forbidden", exception.getMessage());
    }

    @Test
    void createAccount_shouldReturnNotFound_whenRelatedUserNotFound() {
        when(accountService.createAccount(a1))
                .thenThrow(new UserNotFoundException("User not found"));

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> accountController.createAccount(a1)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getSingleAccount_shouldReturnAccount_whenFound() {
        Long id = 1L;
        when(accountService.getSingleAccount(id)).thenReturn(Optional.of(a1));

        Optional<Account> result = accountController.getSingleAccount(id);

        // Assert that the result is an Optional containing the expected account
        assertTrue(result.isPresent());
        assertEquals(a1, result.get());
    }

    @Test
    void getSingleAccount_shouldThrowNotFound_whenMissing() {
        Long id = 50L;
        when(accountService.getSingleAccount(id)).thenReturn(Optional.empty());

        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class,
                () -> accountController.getSingleAccount(id));

        assertEquals("Account "+id+" not found", ex.getMessage());
    }

    @Test
    void getAllAccount_shouldReturnListOfAccounts() {
        List<Account> mockAccounts = Arrays.asList(a1, a1); // assuming a1 and a2 are test Account objects
        when(accountService.getAllAccount()).thenReturn(mockAccounts);

        List<Account> response = accountController.getAllAccount();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(a1, response.get(0));
        assertEquals(a1, response.get(1));
    }


    @Test
    void deposit_shouldReturnUpdatedAccount() {
        long id = 1L;
        double amount = 500.0;
        Map<String, Double> request = Map.of("amount", amount);

        a1.setBalance(a1.getBalance() + amount); // expected updated account

        when(accountService.deposit(id, amount)).thenReturn(a1);

        Account result = accountController.deposit(id, request);

        assertNotNull(result);
        assertEquals(1500.0, result.getBalance());
    }


    @Test
    void withdraw_shouldReturnUpdatedAccount() {
        long id = 1L;
        double amount = 200.0;
        Map<String, Double> request = Map.of("amount", amount);

        a1.setBalance(a1.getBalance() - amount); // expected updated account

        when(accountService.withdraw(id, amount)).thenReturn(a1);

        Account result = accountController.withdraw(id, request);

        assertNotNull(result);
        assertEquals(800.0, result.getBalance());
    }

    @Test
    void deleteAccount_shouldReturnSuccessMessage() {
        Long id = 1L;

        doNothing().when(accountService).deleteAccount(id);

        ResponseEntity<String> response = accountController.deleteAccount(id);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Account deleted successfully", response.getBody());
    }


}