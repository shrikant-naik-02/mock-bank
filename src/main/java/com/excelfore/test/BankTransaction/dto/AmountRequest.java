package com.excelfore.test.BankTransaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body for deposit or withdraw operations")
public class AmountRequest {

    @Schema(description = "Amount to deposit or withdraw", example = "5000")
    @NotEmpty
    private Double amount;
}
