package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.enums.Role;
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

import static org.junit.jupiter.api.Assertions.*;
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
        a1.setBalance(0);
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
    void getSingleAccount() {
    }

    @Test
    void getAllAccount() {
    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void myTest() {
    }
}