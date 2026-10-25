package com.payflow.wallet.assembler;

import com.payflow.wallet.dto.WalletResponse;
import com.payflow.wallet.entity.Wallet;

public class ResponseAssembler {

  public static WalletResponse mapToResponse(Wallet wallet) {
    return WalletResponse.builder()
        .id(wallet.getId())
        .userId(wallet.getUserId())
        .balance(wallet.getBalance())
        .currency(wallet.getCurrency())
        .status(wallet.getStatus())
        .createdAt(wallet.getCreatedAt())
        .build();
  }
}
