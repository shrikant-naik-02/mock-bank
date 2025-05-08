package com.excelfore.test.BankTransaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mock")
public class MockController {

    private final ExternalApiService externalApiService;

    public MockController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping
    public ResponseEntity<String> fetchMockData() {
        String data = externalApiService.getMockData();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/mock-data")
    @Operation(
            summary = "Get mock data from Beeceptor",
            description = "Calls Beeceptor mock server at https://banktransaction.free.beeceptor.com/external-service",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mock data retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Error fetching mock data")
            }
    )
    public ResponseEntity<String> getMockData() {
        String data = externalApiService.getMockData();
        return ResponseEntity.ok(data);
    }

}
