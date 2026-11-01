package com.payflow.payment.assembler;

import com.payflow.payment.dto.PaymentRequest;
import com.payflow.payment.dto.PaymentResponse;
import com.payflow.payment.entity.Payment;
import com.payflow.payment.entity.PaymentStatus;
import com.payflow.payment.event.PaymentEvent;

import java.time.Instant;

public class PaymentAssembler {

	private static String EVENT_TYPE = "PAYMENT_INITIATED";

	public static Payment toPaymentEntity(String idempotencyKey, PaymentRequest paymentRequest) {
		return Payment.builder()
				.idempotencyKey(idempotencyKey)
				.fromWalletId(paymentRequest.getFromWalletId())
				.toWalletId(paymentRequest.getToWalletId())
				.amount(paymentRequest.getAmount())
				.currency(paymentRequest.getCurrency())
				.description(paymentRequest.getDescription())
				.status(PaymentStatus.PROCESSING)
				.build();
	}

	public static PaymentEvent toPaymentEvent(String idempotencyKey, Payment  payment) {
		return PaymentEvent.builder()
				.paymentId(payment.getId())
				.idempotencyKey(idempotencyKey)
				.fromWalletId(payment.getFromWalletId())
				.toWalletId(payment.getToWalletId())
				.amount(payment.getAmount())
				.currency(payment.getCurrency())
				.eventType(EVENT_TYPE)
				.timestamp(Instant.now())
				.build();
	}

	public static PaymentResponse toPaymentResponse(Payment payment, boolean isDuplicate) {
		return PaymentResponse.builder()
				.id(payment.getId())
				.idempotencyKey(payment.getIdempotencyKey())
				.fromWalletId(payment.getFromWalletId())
				.toWalletId(payment.getToWalletId())
				.amount(payment.getAmount())
				.currency(payment.getCurrency())
				.status(payment.getStatus())
				.description(payment.getDescription())
				.failureReason(payment.getFailureResponse())
				.createdAt(payment.getCreatedAt())
				.completedAt(payment.getCompletedAt())
				.duplicate(isDuplicate)
				.build();
	}
}
