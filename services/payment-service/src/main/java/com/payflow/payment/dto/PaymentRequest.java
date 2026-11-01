package com.payflow.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {

  @NotNull(message = "From wallet ID is required")
  private UUID fromWalletId;

  @NotNull(message = "To wallet ID is required")
  private UUID toWalletId;

  @NotNull(message = "Amount is required")
  @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
  private BigDecimal amount;

  @NotNull(message = "Currency is required")
  @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be 3 letters like INR, USD")
  private String currency;

  private String description;
}
