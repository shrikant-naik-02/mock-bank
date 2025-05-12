package com.excelfore.test.BankTransaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body for deposit or withdraw operations")
public class AmountRequest {

    @Schema(description = "Amount to deposit or withdraw", example = "5000")
    private Double amount;
}
