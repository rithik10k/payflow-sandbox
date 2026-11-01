package com.payflow.payment.controller;

import com.payflow.payment.dto.PaymentRequest;
import com.payflow.payment.dto.PaymentResponse;
import com.payflow.payment.entity.Payment;
import com.payflow.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity<PaymentResponse> processPayment(
      @RequestHeader("X-Idempotency-Key") String idempotencyKey,
      @Valid @RequestBody PaymentRequest paymentRequest) {
    log.info("Received Key: {} and Payment Request: {}", idempotencyKey, paymentRequest);
    PaymentResponse paymentResponse = paymentService.processPayment(idempotencyKey, paymentRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
  }

  @GetMapping("/{paymentId}")
  public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID paymentId) {
    PaymentResponse paymentResponse = paymentService.getPayment(paymentId);

    return ResponseEntity.ok(paymentResponse);
  }
}
