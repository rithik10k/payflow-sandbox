package com.payflow.wallet.controller;

import com.payflow.wallet.dto.CreateWalletRequest;
import com.payflow.wallet.dto.WalletResponse;
import com.payflow.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

  private final WalletService walletService;

  @PostMapping
  public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody CreateWalletRequest request) {
    WalletResponse walletResponse = walletService.createWallet(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(walletResponse);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<WalletResponse> getWalletByUserId(@PathVariable UUID userId) {
    WalletResponse walletResponse = walletService.getWalletByUserId(userId);
    return ResponseEntity.ok(walletResponse);
  }

  @GetMapping("/{walletId}")
  public ResponseEntity<WalletResponse> getWalletById(@PathVariable UUID walletId) {
    WalletResponse walletResponse = walletService.getWalletById(walletId);
    return ResponseEntity.ok(walletResponse);
  }
}
