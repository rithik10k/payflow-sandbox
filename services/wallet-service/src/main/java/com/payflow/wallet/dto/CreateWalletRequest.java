package com.payflow.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateWalletRequest {

  @NotNull(message = "User ID is required")
  private UUID userId;

  @NotNull(message = "Currency is required")
  @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter code like INR, USD")
  private String currency;
}
