package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.enums.Role;
import com.excelfore.test.BankTransaction.model.User;
import com.excelfore.test.BankTransaction.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    UserController uc = null;

//    @InjectMocks
//    private UserController userController;

    User u1 = null;

//    @Mock
    UserService service = mock(UserService.class);

    @BeforeEach
    void setUp() {
        uc = new UserController(service);
//        MockitoAnnotations.openMocks(this);
        u1 = new User();
        u1.setRole(Role.valueOf("USER"));
        u1.setPassword("123456");
        u1.setName("unit1");
        u1.setUsername("unit1");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerUser() {
    when(service.createUser(u1)).thenReturn(u1);
//    assertEquals("\"hi junit\"", uc.registerUser(u1));

        assertEquals(HttpStatus.CREATED, uc.registerUser(u1).getStatusCode());
        assertEquals("User registered successfully", uc.registerUser(u1).getBody());


//        ResponseEntity<String> response = userController.registerUser(u1);
//
//        assertEquals(201, response.getStatusCodeValue());
//        assertEquals("User registered successfully", response.getBody());

    }
}