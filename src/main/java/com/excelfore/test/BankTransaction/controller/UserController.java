package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.model.User;
import com.excelfore.test.BankTransaction.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser( @RequestBody User user) {
        userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
