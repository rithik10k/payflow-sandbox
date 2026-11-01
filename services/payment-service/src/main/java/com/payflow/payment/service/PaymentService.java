package com.payflow.payment.service;

import com.payflow.payment.assembler.PaymentAssembler;
import com.payflow.payment.dto.PaymentRequest;
import com.payflow.payment.dto.PaymentResponse;
import com.payflow.payment.entity.Payment;
import com.payflow.payment.entity.PaymentStatus;
import com.payflow.payment.event.PaymentEvent;
import com.payflow.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

  private static final String PAYMENT_TOPIC = "payment-events";

  @Transactional
  public PaymentResponse processPayment(String idempotencyKey, PaymentRequest paymentRequest) {
    log.info("Processing Payment with idempotencyKey: {} and Request: {}", idempotencyKey, paymentRequest);

    // Perform duplicate payment check here and if found return existing stored results.
    var existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);

    if (existingPayment.isPresent()) {
      log.warn("Duplicate payment detected for key: {}", idempotencyKey);
      return PaymentAssembler.toPaymentResponse(existingPayment.get(), true);
    }

    // Perform new payment - process here.
    Payment savedPayment = paymentRepository.save(PaymentAssembler.toPaymentEntity(idempotencyKey, paymentRequest));
    log.info("Payment saved with id: {}", savedPayment.getId());

    // Publish event to kafka - Wallet message service will consume this and update the balances
    kafkaTemplate.send(PAYMENT_TOPIC, idempotencyKey, PaymentAssembler.toPaymentEvent(idempotencyKey, savedPayment));
    log.info("Payment event published to kafka topic: {}", PAYMENT_TOPIC);

    //Update payment status to completed
    savedPayment.setStatus(PaymentStatus.COMPLETED);
    savedPayment.setCompletedAt(Instant.now());
    Payment completedPayment = paymentRepository.save(savedPayment);

    return PaymentAssembler.toPaymentResponse(completedPayment, false);
  }

  @Transactional(readOnly = true)
  public PaymentResponse getPayment(UUID paymentId) {
    log.info("Fetching Payment with id: {}", paymentId);
    Payment payment = paymentRepository.findById(paymentId).orElseThrow(() ->
        new EntityNotFoundException("Payment not found with id: " + paymentId)
    );

    return PaymentAssembler.toPaymentResponse(payment, false);
  }
}
