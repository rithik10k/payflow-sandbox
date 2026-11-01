package com.payflow.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotency_key", unique = true),
    @Index(name = "idx_from_wallet", columnList = "from_wallet_id"),
    @Index(name = "idx_staus", columnList = "status")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "idempotency_key", nullable = false, unique = true, length = 64)
  private String idempotencyKey;

  @Column(name = "from_wallet_id", nullable = false)
  private UUID fromWalletId;

  @Column(name = "to_wallet_id", nullable = false)
  private UUID toWalletId;

  @Column(nullable = false, precision = 19, scale = 4)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status;

  @Column(length = 500)
  private String description;

  @Column(name = "failure_response", length = 500)
  private String failureResponse;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @Column(name = "completed_at")
  private Instant completedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = Instant.now();
    if (status == null){
      status = PaymentStatus.PENDING;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }
}
