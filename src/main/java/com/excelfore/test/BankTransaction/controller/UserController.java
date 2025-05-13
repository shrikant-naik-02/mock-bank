package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.dto.MessageFieldResponse;
import com.excelfore.test.BankTransaction.model.Account;
import com.excelfore.test.BankTransaction.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SecurityRequirement(name = "basicAuth")
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
    @Operation(
            summary = "Create Your Account And Save Your Money",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successful Registered",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string",description = "Account Created Successfully."),
//                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "\"Account Created successfully\"")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Field Is Empty",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string",description = "must not be empty."),
//                                    examples = @ExampleObject(value = "{\"message\": \"must not be empty\"}")
                                    examples = @ExampleObject(value = "\"must not be empty\"")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Invalid Username",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string",description = "Username Already Taken."),
//                                    examples = @ExampleObject(value = "{\"message\": \"Username already taken.\"}")
                                    examples = @ExampleObject(value = "\"Username already taken.\"")
                            )
                    )
            }
    )
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        log.info("Received request to /register with params: {}", user);

//        String responseMessage = "User " + user.getUsername() + " registered successfully";
        String responseMessage = "User registered successfully";

        log.info(responseMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
}
