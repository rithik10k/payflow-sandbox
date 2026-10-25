package com.payflow.wallet.service;

import com.payflow.wallet.assembler.ResponseAssembler;
import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.dto.WalletResponse;
import com.payflow.wallet.entity.Wallet;
import com.payflow.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

  private final WalletRepository walletRepository;

  @Transactional
  public WalletResponse createWallet(CreateWalletRequest request) {
    log.info("Create wallet for userId: {}", request.getUserId());

    if (walletRepository.existsByUserId(request.getUserId())) {
      throw new IllegalStateException(String.format("Wallet already exists for userId: %s", request.getUserId()));
    }

    Wallet wallet = Wallet.builder()
        .userId(request.getUserId())
        .balance(BigDecimal.ZERO)
        .currency(request.getCurrency())
        .build();

    Wallet savedWallet = walletRepository.save(wallet);
    log.info("Wallet created successfully for userId: {}", savedWallet.getUserId());

    return ResponseAssembler.mapToResponse(savedWallet);
  }

  @Transactional(readOnly = true)
  public WalletResponse getWalletByUserId(UUID userId) {
    log.info("Fetching wallet for userId: {}", userId);

    Wallet wallet = walletRepository.findByUserId(userId)
        .orElseThrow(() -> new IllegalStateException(String.format("Wallet not found for userId: %s", userId)));

    return ResponseAssembler.mapToResponse(wallet);
  }

  @Transactional(readOnly = true)
  public WalletResponse getWalletById(UUID id) {
    log.info("Fetching wallet for id: {}", id);

    Wallet wallet = walletRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException(String.format("Wallet not found for id: %s", id)));

    return ResponseAssembler.mapToResponse(wallet);
  }
}
