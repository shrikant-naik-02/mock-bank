package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.enums.Role;
import com.excelfore.test.BankTransaction.exception.UserAlreadyHasAccountException;
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

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User u1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        u1 = new User();
        u1.setRole(Role.USER);
        u1.setPassword("123456");
        u1.setName("unit1");
        u1.setUsername("unit1");
    }

    @Test
    void registerUser_shouldReturnCreatedResponse() {
        when(userService.createUser(u1)).thenReturn(u1);

        ResponseEntity<String> response = userController.registerUser(u1);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void registerUser_shouldReturnConflictResponse() {
        when(userService.createUser(u1)).thenThrow(new UserAlreadyHasAccountException("Username already taken."));

        UserAlreadyHasAccountException exception = assertThrows(
                UserAlreadyHasAccountException.class,
                () -> userController.registerUser(u1)
        );

        assertEquals("Username already taken.", exception.getMessage());
    }
}




//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


//class UserControllerTest {
//
//    UserController uc = null;
//    User u1 = null;
//    UserService service = mock(UserService.class);
//
//    @BeforeEach
//    void setUp() {
//        uc = new UserController(service);
//        u1 = new User();
//        u1.setRole(Role.valueOf("USER"));
//        u1.setPassword("123456");
//        u1.setName("unit1");
//        u1.setUsername("unit1");
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void registerUser() {
//    when(service.createUser(u1)).thenReturn(u1);
//
//        assertEquals(HttpStatus.CREATED, uc.registerUser(u1).getStatusCode());
//        assertEquals("User registered successfully", uc.registerUser(u1).getBody());
//
//    }
//}
