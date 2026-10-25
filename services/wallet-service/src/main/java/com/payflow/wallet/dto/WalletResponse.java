package com.payflow.wallet.dto;

import com.payflow.wallet.entity.WalletStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class WalletResponse {
  private UUID id;
  private UUID userId;
  private BigDecimal balance;
  private String currency;
  private WalletStatus status;
  private Instant createdAt;
}
