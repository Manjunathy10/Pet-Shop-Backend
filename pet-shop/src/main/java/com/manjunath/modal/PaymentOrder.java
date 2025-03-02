package com.manjunath.modal;

import java.util.HashSet;
import java.util.Set;

import com.manjunath.domain.PaymentMethod;
import com.manjunath.domain.PaymentOrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long amount;

	private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

	private PaymentMethod paymentMethod;

	private String paymentLinkId;

	@OneToOne
	private User user;

	@OneToMany
	private Set<Order> orders = new HashSet<>();

}
