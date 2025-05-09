package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        log.info("Received request to /register with params: {}", user);
//
//        String customJson = """
//            {"message": "User %s registered successfully"}
//            """.formatted(user.getUsername()); // Change to appropriate field
//
//        log.info(customJson);
//        return ResponseEntity.status(HttpStatus.CREATED).body(customJson);
//    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        log.info("Received request to /register with params: {}", user);

//        String responseMessage = "User " + user.getUsername() + " registered successfully";
        String responseMessage = "User registered successfully";

        log.info(responseMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
}
