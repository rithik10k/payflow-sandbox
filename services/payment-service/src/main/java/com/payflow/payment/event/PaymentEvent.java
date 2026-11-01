package com.payflow.payment.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
  private UUID paymentId;
  private String idempotencyKey;
  private UUID fromWalletId;
  private UUID toWalletId;
  private BigDecimal amount;
  private String currency;
  private String eventType;
  private Instant timestamp;
}
