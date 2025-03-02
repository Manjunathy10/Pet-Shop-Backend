package com.manjunath.modal;

import com.manjunath.domain.PaymentStatus;

import lombok.Data;

@Data
public class PaymentDetails {

	private String paymentId;
	private String razorpayPaymentLinkId;
	private String razorpayPaymentLinkReferenceld;
	private String razorpayPaymentLinkStatus;
	private String razorpayPaymentIdZWSP;
	private PaymentStatus status;

}
