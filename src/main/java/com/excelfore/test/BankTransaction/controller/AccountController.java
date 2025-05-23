package com.excelfore.test.BankTransaction.controller;

import com.excelfore.test.BankTransaction.dto.AmountRequest;
import com.excelfore.test.BankTransaction.dto.MessageFieldResponse;
import com.excelfore.test.BankTransaction.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SecurityRequirement(name = "basicAuth")
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @PostMapping
    @Operation(
            summary = "Create Your Account And Save Your Money",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successful Account Creation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Account.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invalid Username - Username Not present In List Of User",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(
                                            name = "UserNotFoundExample",
                                            value = "{\"message\": \"No user found with username: Something\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Invalid Username - User Already Exist",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"User already has an account.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws JsonProcessingException {
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

//    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/single-account/{id}")
    @Operation(
            summary = "Get Your Account Details Based On Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully Fetched The Account",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Account.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Invalid Account Id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"Account 50 not found\"}")
                            )
                    )
            }
    )
    public Optional<Account> getSingleAccount(@PathVariable Long id) throws JsonProcessingException {
        log.info("Received request to / with id: {}", id);

        String json = """
        {
            "id": %s,
            "accountHolderName": "user9",
            "balance": 1010.0,
            "createdAt": "2025-05-07T10:43:53.777177",
            "updatedAt": "2025-05-07T10:43:53.777262",
            "user": {
                "id": 10,
                "name": "user9",
                "username": "user9",
                "password": "$2a$10$BFQiJ.t6PtwFp9Dj4fNfIOkFExATIEaAuX4VWF6HMTzqeqIbn/mJ6",
                "role": "USER",
                "createdAt": "2025-05-07T09:56:45.001769",
                "updatedAt": "2025-05-07T09:56:45.001798"
            }
        }
    """.formatted(id);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Account mockAccount = mapper.readValue(json, Account.class);

        return Optional.of(mockAccount);
    }


    @GetMapping("/all")
    public List<Account> getAllAccount() throws JsonProcessingException {
        log.info("Received request to /all");

        String json = """
        [
                        {
                            "id": 4,
                            "accountHolderName": "user8",
                            "balance": 1010.0,
                            "createdAt": "2025-05-07T10:04:07.03341",
                            "updatedAt": "2025-05-07T10:04:07.033441",
                            "user": {
                                "id": 9,
                                "name": "user8",
                                "username": "user8",
                                "password": "$2a$10$YTxtwetWOpDeD8MBP/HF7O/GN1xAd68gnT5.ou.LdkT10yzzyLdqy",
                                "role": "USER",
                                "createdAt": "2025-05-07T09:50:38.207514",
                                "updatedAt": "2025-05-07T09:50:38.207647"
                            }
                        },
                        {
                            "id": 5,
                            "accountHolderName": "user9",
                            "balance": 1010.0,
                            "createdAt": "2025-05-07T10:43:53.777177",
                            "updatedAt": "2025-05-07T10:43:53.777262",
                            "user": {
                                "id": 10,
                                "name": "user9",
                                "username": "user9",
                                "password": "$2a$10$BFQiJ.t6PtwFp9Dj4fNfIOkFExATIEaAuX4VWF6HMTzqeqIbn/mJ6",
                                "role": "USER",
                                "createdAt": "2025-05-07T09:56:45.001769",
                                "updatedAt": "2025-05-07T09:56:45.001798"
                            }
                        },
                        {
                            "id": 9,
                            "accountHolderName": "user18",
                            "balance": 1010.0,
                            "createdAt": "2025-05-07T12:59:46.666469",
                            "updatedAt": "2025-05-07T12:59:46.666778",
                            "user": {
                                "id": 27,
                                "name": "user18",
                                "username": "user18",
                                "password": "$2a$10$.0RGGEaK7T9phtfjbry4wuSeklsk9ZPlpy6r6KGOee8ikRmha443G",
                                "role": "USER",
                                "createdAt": "2025-05-07T12:38:25.48999",
                                "updatedAt": "2025-05-07T12:38:25.490154"
                            }
                        },
                        {
                            "id": 11,
                            "accountHolderName": "user20",
                            "balance": 1010.0,
                            "createdAt": "2025-05-07T14:45:12.491466",
                            "updatedAt": "2025-05-07T14:45:12.491537",
                            "user": {
                                "id": 29,
                                "name": "user20",
                                "username": "user20",
                                "password": "$2a$10$DUAc6t7AL.Z.FJSXMWAOUOGaqnD5yaHtdz3AYx0Gz9/k5ehAjMamu",
                                "role": "USER",
                                "createdAt": "2025-05-07T12:44:17.04906",
                                "updatedAt": "2025-05-07T12:44:17.049264"
                            }
                        }
                    ]
    """;

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(json, new TypeReference<List<Account>>() {});
    }

    @PutMapping("/{id}/deposit")
    @Operation(
            summary = "Deposit Money Into Account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully Deposited",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Account.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"Account Not Found May Be Not Yet Created.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"You are not authorized to perform this action.\"}")
                            )
                    )
            }
    )
    public Account deposit(@PathVariable Long id, @RequestBody AmountRequest request) throws JsonProcessingException {
        Double amount = request.getAmount();

        // Mock Account update (deposit)
        String json = """
            {
                "id": %s,
                "accountHolderName": "user2",
                "balance": %.2f,
                "createdAt": "2025-05-02T17:03:30.069816",
                "updatedAt": "2025-05-09T14:52:20.997002",
                "user": {
                    "id": 3,
                    "name": "user2",
                    "username": "user2",
                    "password": "$2a$10$DC6yl0D8DBPKh7jE0dVzmuzI2/3RKFIA.Bgezrjm.mbqAqba9YsAe",
                    "role": "USER",
                    "createdAt": "2025-05-02T17:01:20.736666",
                    "updatedAt": "2025-05-02T17:01:20.736715"
                }
            }
    """.formatted(id, amount); // Customize the balance according to the operation

        // Map JSON to Account
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(json, Account.class);
    }

    @PutMapping("/{id}/withdraw")
    @Operation(
            summary = "Withdraw Money From Account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful Withdraw",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Account.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Insufficient funds",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"Insufficient funds to withdraw.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"You are not authorized to perform this action.\"}")
                            )
                    )
            }
    )
    public Account withdraw(@PathVariable Long id, @RequestBody AmountRequest request) throws JsonProcessingException {
        Double amount = request.getAmount();
        String json = """
            {
                "id": %s,
                "accountHolderName": "user2",
                "balance": %.2f,
                "createdAt": "2025-05-02T17:03:30.069816",
                "updatedAt": "2025-05-09T14:51:56.393579",
                "user": {
                    "id": 3,
                    "name": "user2",
                    "username": "user2",
                    "password": "$2a$10$DC6yl0D8DBPKh7jE0dVzmuzI2/3RKFIA.Bgezrjm.mbqAqba9YsAe",
                    "role": "USER",
                    "createdAt": "2025-05-02T17:01:20.736666",
                    "updatedAt": "2025-05-02T17:01:20.736715"
                }
            }
    """.formatted(id, amount); // Customize the balance accordingly

        // Map JSON to Account
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(json, Account.class);
    }

    @Operation(
            summary = "Delete The UnUsed Or Closed Account From Your Bank",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "string", description = "Account deleted successfully"),
                                    examples = @ExampleObject(value = "\"Account deleted successfully\"")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"You are not authorized to perform this action.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageFieldResponse.class),
                                    examples = @ExampleObject(value = "{\"message\": \"Account Not Found May Be Not Yet Created.\"}")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        log.info("Received request to /{id}} with value: {}", id);

        String responseMessage = "Account deleted successfully";

        log.info(responseMessage);
        return ResponseEntity.ok(responseMessage);
    }
}
