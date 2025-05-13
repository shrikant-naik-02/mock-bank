package com.excelfore.test.BankTransaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "A Simple And Clear response message")
@Getter
@Setter
public class MessageFieldResponse {

    @Schema(description = "The response message", example = "Account 500 not found")
    private String message;

    public MessageFieldResponse() {}

    public MessageFieldResponse(String message) {
        this.message = message;
    }

}
