package com.payflow.payment.dto;

import com.payflow.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class PaymentResponse {
  private UUID id;
  private String idempotencyKey;
  private UUID fromWalletId;
  private UUID toWalletId;
  private BigDecimal amount;
  private String currency;
  private PaymentStatus status;
  private String description;
  private String failureReason;
  private Instant createdAt;
  private Instant completedAt;
  private boolean duplicate;
}
