package com.payflow.wallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "wallets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "user_id", nullable = false, unique = true)
  private UUID userId;

  @Column(nullable = false, precision = 19, scale = 4)
  private BigDecimal balance;

  @Column(nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WalletStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    updatedAt = Instant.now();

    if (balance == null) {
      balance = BigDecimal.ZERO;
    }

    if (status == null) {
      status = WalletStatus.ACTIVE;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }
}